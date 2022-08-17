package com.quasi.template.demo.diypool.base

import androidx.databinding.ViewDataBinding

abstract class VSBaseDialogViewModel<viewDataBinding : ViewDataBinding> {
    abstract fun setViewDataBinding(viewDataBinding: ViewDataBinding)
}