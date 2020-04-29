package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pretty.core.arch.*
import com.pretty.core.arch.commonpage.CommonPageManager
import com.pretty.core.arch.commonpage.ICommonPage


/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseFragment<B : ViewDataBinding> : Fragment(), IView, ILoadable {

    protected lateinit var mBinding: B
    protected abstract val mLayoutId: Int
    override val mDisplayDelegate: IDisplayDelegate by lazy { DisplayDelegate() }
    override val mDisposableManager: IDisposableManager by lazy { DisposableManager() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDisposableManager.init(this)
        mBinding = DataBindingUtil.inflate<B>(inflater, mLayoutId, container, false)
            .apply { lifecycleOwner = this@BaseFragment }
        mDisplayDelegate.init(activity!!, createCommonPage(mBinding.root))
        subscribeBasic(mBinding)
        return mBinding.root
    }

    @CallSuper
    open fun subscribeBasic(binding: B) {
        val viewModel = getViewModel() ?: ViewModelProvider(this).get(BaseViewModel::class.java)
        if (viewModel is BaseViewModel) {
            viewModel.tips.observe(viewLifecycleOwner, Observer {
                mDisplayDelegate.showTips(it)
            })
            viewModel.loading.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is LoadingState.Loading -> showLoading(it.message)
                    is LoadingState.Hide -> hideLoading()
                }
            })
        }
        subscribeUi(binding)
    }

    override fun showLoading(message: String?) {
        mDisplayDelegate.showLoading(message)
    }

    override fun hideLoading() {
        mDisplayDelegate.dismissLoading()
    }

    abstract fun getViewModel(): ViewModel?

    abstract fun subscribeUi(binding: B)

    override fun createCommonPage(contentView: View): ICommonPage? {
        // 返回null不会自动注入empty、loading、error三个布局
        if (injectCommonPage()) {
            return CommonPageManager.getDefault().wrap(contentView).withRetry { reTry() }
        }
        return null
    }

    /**
     * 是否注入默认的CommonPage
     */
    open fun injectCommonPage(): Boolean = false

    /**
     * 注入CommonPage后点击，错误页面或空页面点击重试会回调这里
     */
    open fun reTry() {

    }

}