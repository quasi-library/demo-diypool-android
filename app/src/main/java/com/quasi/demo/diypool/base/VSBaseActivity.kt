package com.quasi.template.demo.diypool.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.quasi.demo.diypool.R
import com.quasi.demo.diypool.databinding.LayoutNavigationTitleBarBinding


abstract class VSBaseActivity<viewDataBinding : ViewDataBinding, ViewModel : VSBaseViewModel> :
    AppCompatActivity() {

    //<editor-fold desc="Abstract Imp Method">

    /**
     * Activity对应的布局文件；分解初始化ContentView方法，在基类OnCreate方法调用
     */
    abstract var layoutXmlResourceId: Int

    /**
     * Activity对应ViewModel的类；分解初始化ViewModel方法
     */
    abstract var viewModelClass: Class<ViewModel>

    /**
     * Activity中绑定控件的抽象方法，用于子类实现初始化控件/控件交互
     */
    abstract fun addSubView()

    /**
     * Activity中实现页面和数据双向绑定的方法，用于监听ViewModel中的LiveData
     */
    abstract fun bindViewModel()

    /**
     * 左边的动态布局
     */
    private lateinit var mLeftBarGroupLayout: LinearLayoutCompat

    /**
     * 右边的动态布局
     */
    private lateinit var mRightBarGroupLayout: LinearLayoutCompat

    /**
     * 中间的动态布局(标题左侧)
     */
    private lateinit var mCenterBarGroupLayout: LinearLayoutCompat

    //</editor-fold>


    //<editor-fold desc="Subclass Set Method">

    open fun hideTitleBar(): Boolean = false

    open fun getIntentData() {}

    open fun hideBackIcon() = false

    //</editor-fold>

    //<editor-fold desc="Public Property">

    /**
     * 方便打印Log声明TAG
     */
    val LOG_TAG: String by lazy { this.javaClass.simpleName }

    /**
     * 管理页面数据内容的ViewModel
     */
    lateinit var mViewModel: ViewModel

    /**
     * 管理页面控件的DataBinding
     */
    lateinit var mBinding: viewDataBinding

    /**
     * 管理自定义标题栏的LayoutBinding
     */
    lateinit var titleBinding: LayoutNavigationTitleBarBinding

    /**
     * 是否自动显示Loading，默认自动显示
     */
    var autoShowLoading = true

    /**
     * 设置
     */
    //</editor-fold>

    //<editor-fold desc="Base  Private Property">
    private lateinit var contentLayout: LinearLayout

    /**
     * 默认loading动画的构造器
     */
    private val mLoadingBuilder = UniversalLoading.Builder()

    /**
     * 默认的loading悬浮动画
     */
    private val mLoadingHUD: UniversalLoading by lazy {
        mLoadingBuilder.createLoading(
            this,
            VSLoadingHUDStyle.DEFAULT,
            VSLoadingHUDType.NO_MASK_WITH_OPERATE
        )
    }
    //</editor-fold>

    //<editor-fold desc="Lifecycle Method">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 禁用横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // 方便控制打印当前展示的Activity && 管理页面栈
        Log.d("VSBaseActivity", javaClass.simpleName)
        VSActivityCollector.addActivity(this)

        // UI Layout  Method
        // 加载主视图容器
        initContentView()
        // 加载自定义的标题栏
        initTitleBar()
        // 加载子视图；基类调用之后，子类不需要再显式调用
        setContentView(layoutXmlResourceId)
        // 双向绑定，监听ViewModel
        initViewModel()
        bindViewModel()
        // 更新自定义导航栏,子Activity会在addSubView中自定义标题栏，所以默认、
        // 的设置需要在addSubView执行
        updateNavigationBarStyle(vsNavigationBarStyle)
        // 更新子视图布局
        addSubView()
    }

    override fun onDestroy() {
        super.onDestroy()

        VSActivityCollector.removeActivity(this)
    }
    //</editor-fold>

    //<editor-fold desc="UILayout Method">

    /// 设置viewModel
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[viewModelClass]
        mViewModel.loadingLiveData.observe(this) {
            if (autoShowLoading) {
                if (it) {
                    showLoading()
                } else {
                    dismissLoading()
                }
            }
        }
        mViewModel.requestErrorLiveData.observe(this) {
            vsShowShortToast(it)
        }
    }

    /// 添加容器View(后续添加自定义的navigationXML和子类传入的layoutXML)
    private fun initContentView() {
        val content = findViewById<ViewGroup>(android.R.id.content)
        content.removeAllViews()
        contentLayout = LinearLayout(this)
        contentLayout.orientation = LinearLayout.VERTICAL
        contentLayout.setBackgroundColor(vsGetColor(R.color.app_main_white))
        content.addView(contentLayout)
    }

    // 初始化动态BarItem布局
    private fun initBarItem() {
        mLeftBarGroupLayout = findViewById(R.id.titleBarLeftContainer)
        mLeftBarGroupLayout.gravity = Gravity.START
        mLeftBarGroupLayout.setVerticalGravity(Gravity.CENTER_VERTICAL)

        mRightBarGroupLayout = findViewById(R.id.titleBarRightContainer)
        mRightBarGroupLayout.gravity = Gravity.END
        mRightBarGroupLayout.setVerticalGravity(Gravity.CENTER_VERTICAL)

        mCenterBarGroupLayout = findViewById(R.id.titleBarCenterContainer)
        mCenterBarGroupLayout.gravity = Gravity.CENTER
        mCenterBarGroupLayout.setVerticalGravity(Gravity.CENTER_VERTICAL)
    }

    // 添加左边自定义BarItem
    fun addLeftBarItem(view: VSBaseBarItemView) {
        if (view.parent == null) {
            mLeftBarGroupLayout.addView(view)
        }
        mLeftBarGroupLayout.visibility = View.VISIBLE
    }

    // 添加右边自定义BarItem
    fun addRightBarItem(view: VSBaseBarItemView) {
        if (view.parent == null) {
            mRightBarGroupLayout.addView(view)
        }
        mRightBarGroupLayout.visibility = View.VISIBLE
    }

    // 添加导航栏中间区域（标题自定义）的BarItem
    fun addCentralBarItem(view: VSBaseBarItemView) {
        if (view.parent == null) {
            mCenterBarGroupLayout.addView(view)
        }
    }

    // 移除导航栏中间区域（标题自定义）的BarItem
    fun removeCentralBarItem() {
        if (mCenterBarGroupLayout.childCount > 0) {
            mCenterBarGroupLayout.removeAllViews()
        }
    }

    // 恢复标题栏,一般只做在自定义导航栏中间区域后恢复标题用
    fun revertCentralTitle() {
        removeCentralBarItem()
        mCenterBarGroupLayout.addView(titleBinding.titleBarTitleLabel.apply {
            text = vsNavigationTitle
        })
    }

    // 在标题前添加自定义View
    fun addExtraCentralBarItem(view: VSBaseBarItemView) {
        removeCentralBarItem()
        mCenterBarGroupLayout.gravity = Gravity.CENTER
        mCenterBarGroupLayout.addView(view)
        mCenterBarGroupLayout.addView(titleBinding.titleBarTitleLabel.apply {
            text = vsNavigationTitle
            this.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 0f
            )
        })
    }


    /// 添加自定义的标题栏
    private fun initTitleBar() {
        // 隐藏默认的NavigationBar
        supportActionBar?.hide()
        // 加载自定义的导航栏
        titleBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.layout_navigation_title_bar,
                contentLayout,
                true
            )
        if (hideTitleBar()) {
            titleBinding.root.visibility = View.GONE
        }
        titleBinding.titleBarBackItem.setOnClickListener {
            onBackPressed()
        }
        if (hideBackIcon()) {
            titleBinding.titleBarBackItem.visibility = View.GONE
        }

        initBarItem()
    }

    override fun setContentView(layoutResID: Int) {
        mBinding = DataBindingUtil.inflate(layoutInflater, layoutResID, contentLayout, true)
        getIntentData()
    }

    /**
     * 设置状态栏图标的颜色
     */
    private fun setDarkStatusIcon(isDark: Boolean) {
        val decorView = window.decorView
        var vis = decorView.systemUiVisibility
        vis = if (isDark) {
            vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        decorView.systemUiVisibility = vis
    }

    enum class VSNavigationBarStyle {
        WHITE_BG_BLACK_TITLE, // 白底黑字(常见于电商页面)
        WHITE_BG_GREEN_TITLE, // 黑底绿字(常见于Device页面)
        BLACK_BG_GREEN_TITLE, // 黑底绿字(常见于Controller页面)
    }

    /**
     * 自定义导航栏标题; 子类可override; 需要注意使用vsGetString替代getString
     */
    open var vsNavigationTitle: String = ""
        set(value) {
            field = value
            titleBinding.titleBarTitleLabel.text = value
        }

    /**
     * 自定导航栏的风格（e.g. 白底黑字 / 黑底绿字）; 子类可override
     */
    open var vsNavigationBarStyle = VSNavigationBarStyle.WHITE_BG_BLACK_TITLE

    /**
     * 更新自定义顶部导航栏的风格(子类想要修改的话，override navigationBarStyle)
     */
    open fun updateNavigationBarStyle(style: VSNavigationBarStyle) {
        var backgroundColor = R.color.app_main_white
        var frontColor = R.color.app_text_title

        when (style) {
            VSNavigationBarStyle.WHITE_BG_BLACK_TITLE -> {
                backgroundColor = R.color.app_main_white
                frontColor = R.color.app_text_title
            }

            VSNavigationBarStyle.WHITE_BG_GREEN_TITLE -> {
                backgroundColor = R.color.app_main_white
                frontColor = R.color.app_main_design_green
            }

            VSNavigationBarStyle.BLACK_BG_GREEN_TITLE -> {
                backgroundColor = R.color.app_text_title
                frontColor = R.color.app_main_design_green
            }
        }

        // 更新导航栏风格
        titleBinding.vsNavBarContainer.background = ContextCompat.getDrawable(this, backgroundColor)
        titleBinding.titleBarTitleLabel.setTextColor(vsGetColor(frontColor))
        titleBinding.titleBarBackItem.setColorFilter(vsGetColor(frontColor))

        // 更新导航栏文案
        titleBinding.titleBarTitleLabel.text = vsNavigationTitle

        // 更新系统状态栏,搭配导航栏样式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = vsGetColor(backgroundColor)
        if (window.statusBarColor == vsGetColor(R.color.app_main_white)) {
            setDarkStatusIcon(true)
        } else {
            setDarkStatusIcon(false)
        }
    }

    /**
     * 展示菊花图
     * @param style 展示的图片资源
     * @param type 展示的菊花图可操作类型
     */
    fun showLoading(
        style: VSLoadingHUDStyle = VSLoadingHUDStyle.DEFAULT,
        type: VSLoadingHUDType = VSLoadingHUDType.NO_MASK_WITH_OPERATE
    ) {
        if (!mLoadingHUD.isShowing) {
            mLoadingHUD.show()
            mLoadingBuilder.updateHUDStyle(style)
            mLoadingBuilder.updateHUDType(type)
        }
    }

    /**
     * 隐藏菊花图
     */
    fun dismissLoading() {
        if (mLoadingHUD.isShowing) {
            mLoadingHUD.dismiss()
        }
    }


//</editor-fold>
}