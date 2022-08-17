package com.quasi.demo.diypool.ui

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.quasi.demo.diypool.R
import com.quasi.demo.diypool.databinding.ActivityDiyKitPoolBinding
import com.quasi.demo.diypool.databinding.SectionDiyChooseNorBinding
import com.quasi.demo.diypool.model.DiyPoolModel
import com.quasi.template.demo.diypool.base.VSBaseActivity
import com.quasi.template.demo.diypool.base.VSBaseBarItemView
import com.quasi.template.demo.diypool.base.vsGetDrawable
import com.quasi.template.demo.diypool.base.vsGetString
import com.quasi.template.demo.diypool.model.DiyCombinationModel

/**
 * 自选池商品页面
 */
class DiyKitPoolActivity : VSBaseActivity<ActivityDiyKitPoolBinding, DiyKitPoolViewModel>() {

    //<editor-fold desc="Init Method">

    companion object {

        // 初始化页面时携带的上个页面传入的值
        private const val INTENT_EXTRA_DEFAULT_DATA = "INTENT_EXTRA_DEFAULT_DATA"

        /**
         * 提供跳转方法进入"DiyKitPool"页面，不回调在此页面的操作
         * @param originActivity 来源页面
         * @param defaultSelectIds 根据调查问卷已经默认选中的商品
         */
        fun showActivity(
            originActivity: AppCompatActivity,
            defaultSelectIds: ArrayList<DiyCombinationModel>?
        ) {
            val intent = Intent(originActivity, DiyKitPoolActivity::class.java)
            intent.putParcelableArrayListExtra(INTENT_EXTRA_DEFAULT_DATA, defaultSelectIds)
            originActivity.startActivity(intent)
        }

    }

    //</editor-fold>

    //<editor-fold desc="Bind Method">
    override var viewModelClass: Class<DiyKitPoolViewModel> = DiyKitPoolViewModel::class.java
    override fun bindViewModel() {
        mViewModel.mDefaultIds = intent.getParcelableArrayListExtra(INTENT_EXTRA_DEFAULT_DATA)

        mViewModel.refreshListLiveData.observe(this) {
            mListAdapter.models = it
        }
    }
    //</editor-fold>

    //<editor-fold desc="UI Layout Method">
    override var vsNavigationTitle: String = vsGetString(R.string.app_shop_diy_kit_title)
    override var layoutXmlResourceId: Int = R.layout.activity_diy_kit_pool

    override fun addSubView() {
        // 添加导航栏按钮
        addNavigationBarItems()

        // 布局列表
        addListView()

        // 请求当前商品池所有选项
        mViewModel.requestDiyPoolProducts()
    }

    private fun addNavigationBarItems() {
        // 右侧重置按钮
        val resetImageView = ImageView(this)
        resetImageView.setImageDrawable(vsGetDrawable(R.drawable.nav_icon_reset))
        val resetItem = VSBaseBarItemView(
            this,
            onClickListener = {
                clickResetAction()
            },
            customizeView = resetImageView
        )
        addRightBarItem(resetItem)

        // 右侧问号按钮
        val questionImageView = ImageView(this)
        questionImageView.setImageDrawable(vsGetDrawable(R.drawable.nav_icon_help))
        val questionItem = VSBaseBarItemView(
            this,
            onClickListener = {
                clickQuestionAction()
            },
            customizeView = questionImageView
        )
        addRightBarItem(questionItem)
    }

    private var mListAdapter = BindingAdapter()

    private fun addListView() {
        // 创建竖项列表
        mListAdapter = mBinding.rv.linear().setup {
            // 声明添加section头
            addType<DiyPoolModel>(R.layout.section_diy_choose_nor)

            // 视图和数据绑定
            onBind {
                // 标题行
                val sectionBinding = this.getBinding<SectionDiyChooseNorBinding>()
                val sectionModel = this.getModel<DiyPoolModel>()
                sectionBinding.sectionTitleLabel.text =
                    StringBuilder().append(sectionModel.title).append(" *")
            }
        }
    }

    //</editor-fold>

    //<editor-fold desc="UI Action Method">

    private fun clickResetAction() {

    }

    private fun clickQuestionAction() {

    }

    //</editor-fold>

    //<editor-fold desc="Private Method">

    //</editor-fold>

    //<editor-fold desc="Tracker Method">

    //</editor-fold>

}