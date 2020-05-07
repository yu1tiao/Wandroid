package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pretty.core.arch.*
import com.pretty.core.arch.commonpage.CommonPageManager
import com.pretty.core.arch.commonpage.ICommonPage
import com.pretty.core.ext.observe


/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseFragment : Fragment(), IView, ILoadable {

    protected abstract val mLayoutId: Int
    override val mDisplayDelegate: IDisplayDelegate by lazy { DisplayDelegate() }
    override val mDisposableManager: IDisposableManager by lazy { DisposableManager() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDisposableManager.init(this)
        val root = inflateView(inflater, container)
        mDisplayDelegate.init(activity!!, createCommonPage(root))
        subscribeBase()
        return root
    }

    protected open fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(mLayoutId, container, false)
    }

    @CallSuper
    open fun subscribeBase() {
        val viewModel = getViewModel() ?: ViewModelProvider(this).get(BaseViewModel::class.java)
        if (viewModel is BaseViewModel) {
            observe(viewModel.tips) { mDisplayDelegate.showTips(it) }
            observe(viewModel.loading) {
                when (it) {
                    is LoadingState.Loading -> showLoading(it.message)
                    is LoadingState.Hide -> hideLoading()
                }
            }
            observe(viewModel.tips) { mDisplayDelegate.showTips(it) }
        }
    }

    abstract fun getViewModel(): ViewModel?

    override fun showLoading(message: String?) {
        mDisplayDelegate.showLoading(message)
    }

    override fun hideLoading() {
        mDisplayDelegate.dismissLoading()
    }

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