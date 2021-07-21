package com.pretty.core.base

import android.view.View
import androidx.databinding.ViewDataBinding


/**
 * 支持DataBinding
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseDataBindActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity<VM>() {

    protected lateinit var mBinding: B

    override fun prepareContentView(): View {
        mBinding = initBinding()
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    abstract fun initBinding(): B

}