package com.pretty.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.pretty.core.arch.ILoadable


/**
 * 支持DataBinding
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseDataBindFragment<B : ViewDataBinding> : BaseFragment(), IView, ILoadable {

    protected lateinit var mBinding: B

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate<B>(inflater, mLayoutId, container, false)
            .apply { lifecycleOwner = this@BaseDataBindFragment }
        return mBinding.root
    }
}