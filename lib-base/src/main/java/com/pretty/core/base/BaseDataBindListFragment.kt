package com.pretty.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDataBindListFragment<D, B : ViewDataBinding, VM : BaseListViewModel<D>>
    : BaseListFragment<D, VM>() {

    protected lateinit var mBinding: B

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate<B>(inflater, mLayoutId, container, false)
            .apply { lifecycleOwner = this@BaseDataBindListFragment }
        return mBinding.root
    }
}