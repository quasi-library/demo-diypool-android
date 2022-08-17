package com.quasi.demo.diypool.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.quasi.demo.diypool.R
import com.quasi.template.demo.diypool.base.VSBaseDialogViewModel

class VSBaseDialogFragment<viewDataBinding : ViewDataBinding> : AppCompatDialogFragment() {

    private lateinit var mViewDataBinding: viewDataBinding
    private var mLayoutId: Int = -1
    private lateinit var mViewModel: VSBaseDialogViewModel<viewDataBinding>
    private var mCanCancel: Boolean = false
    private var mFullOfWidth: Boolean = false
    private var mFullOfHeight: Boolean = false
    private var mShowInBottom: Boolean = false
    private var mIsShowTopRadius: Boolean = true
    private var mDismissListener: () -> Unit = {}

    companion object {
        fun <viewDataBinding : ViewDataBinding> getInstance(
            layoutId: Int,
            viewModel: VSBaseDialogViewModel<viewDataBinding>,
            canCancel: Boolean = false,
            fullOfWidth: Boolean = false,
            showInBottom: Boolean = false,
            fullOfHeight: Boolean = false,
            isShowTopRadius: Boolean = true,
            dismissListener: () -> Unit = {},
        ): VSBaseDialogFragment<viewDataBinding> {
            return VSBaseDialogFragment<viewDataBinding>().apply {
                mLayoutId = layoutId
                mViewModel = viewModel
                mCanCancel = canCancel
                mFullOfWidth = fullOfWidth
                mShowInBottom = showInBottom
                mFullOfHeight = fullOfHeight
                mIsShowTopRadius = isShowTopRadius
                mDismissListener = dismissListener
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewDataBinding = DataBindingUtil.inflate(inflater, mLayoutId, null, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!mCanCancel) {
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setOnKeyListener { _, keyCode, event ->
                keyCode == KeyEvent.KEYCODE_BACK
            }
        }

        if (mFullOfWidth) {
            dialog?.window?.apply {
                val layoutParams = this.attributes
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            }
        }

        if (mFullOfHeight) {
            dialog?.window?.apply {
                val layoutParams = this.attributes
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            }
        }

        if (mShowInBottom) {
            dialog?.window?.apply {
                val layoutParams = this.attributes
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.gravity = Gravity.BOTTOM
                if (mIsShowTopRadius) {
                    this.setBackgroundDrawableResource(R.drawable.background_black_line_white_top_radius_bg)
                } else {
                    this.setBackgroundDrawableResource(R.drawable.background_black_line_white_bg)
                }
            }
        }
        mViewModel.setViewDataBinding(mViewDataBinding)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mDismissListener()
    }

}