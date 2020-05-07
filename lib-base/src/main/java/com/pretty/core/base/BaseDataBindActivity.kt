package com.pretty.core.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.pretty.core.arch.ILoadable


/**
 * 支持DataBinding
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseDataBindActivity<B : ViewDataBinding> : BaseActivity(), IView, ILoadable {

    protected lateinit var mBinding: B

    override fun prepareContentView(): View {
        mBinding = DataBindingUtil.setContentView<B>(this, mLayoutId)
            .apply { lifecycleOwner = this@BaseDataBindActivity }
        return mBinding.root
    }

}