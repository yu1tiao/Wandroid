package com.pretty.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding


/**
 * 支持DataBinding
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseDataBindFragment<B : ViewDataBinding, VM : BaseViewModel>
    : BaseFragment<VM>() {

    protected lateinit var mBinding: B

    override fun prepareContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = initBinding(inflater, container)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): B
}