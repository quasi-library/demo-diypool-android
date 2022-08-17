package com.quasi.template.demo.diypool.base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.quasi.demo.diypool.R
import com.quasi.demo.diypool.databinding.LayoutLoadingBinding

/**
 * 统一风格的LoadingHUD
 */
class UniversalLoading : Dialog {

    private lateinit var mBinding: LayoutLoadingBinding

    private constructor(context: Context) : super(context) {
        initView()
    }

    private constructor(context: Context, themeStyle: Int) : super(context, themeStyle) {
        initView()
    }

    private fun initView() {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_loading,
            null,
            false
        )
        setContentView(mBinding.root)
    }

    class Builder {

        private lateinit var dialog: UniversalLoading

        fun createLoading(
            context: Context,
            loadingStyle: VSLoadingHUDStyle,
            loadingStatus: VSLoadingHUDType
        ): UniversalLoading {
            // 初始化动画
            dialog = UniversalLoading(context)
            // 根据枚举加载设置
            this.updateHUDStyle(loadingStyle)
            // 渲染
            dialog.mBinding.loadingAnimation.playAnimation()
            // 更新遮罩层操作类型
            this.updateHUDType(loadingStatus)
            return dialog
        }

        //<editor-fold desc="Public Set Method">
        fun updateHUDType(loadingStatus: VSLoadingHUDType) {
            when (loadingStatus) {
                // 无遮罩，可进行其他的操作
                VSLoadingHUDType.NO_MASK_WITH_OPERATE -> {
                    val dialogWindow = dialog.window
                    dialogWindow?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialogWindow?.setDimAmount(0f)
                    val layoutParams = dialogWindow?.attributes
                    layoutParams?.flags =
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL // 触发弹窗之外的点击事件
                    dialogWindow?.attributes = layoutParams
                }
                VSLoadingHUDType.MASK_WITH_CANCEL -> {
                    // 有遮罩层，点击取消
                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                    dialog.window?.setDimAmount(0f)
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    dialog.mBinding.loadingContainer.setOnClickListener {
                        dialog.cancel()
                    }
                }
                // 有遮罩，无法点击取消
                VSLoadingHUDType.MASK_NO_OPERATE -> {
                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                    dialog.window?.setDimAmount(0f)
                    dialog.setCanceledOnTouchOutside(false)
                    dialog.window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        }

        fun updateHUDStyle(loadingStyle: VSLoadingHUDStyle) {
            val mImageAssetsFolder = "images/"

            // 展示的loading动画文件
            val mAnimation = when (loadingStyle) {
                VSLoadingHUDStyle.SIMPLE -> {
                    "normal_loading.json"
                }
                VSLoadingHUDStyle.BRAND -> {
                    "iot_main_animation.json"
                }
                else -> {
                    "normal_loading.json"
                }
            }

            dialog.mBinding.loadingAnimation.imageAssetsFolder = mImageAssetsFolder
            dialog.mBinding.loadingAnimation.setAnimation(mAnimation)
        }
        //</editor-fold>
    }
}