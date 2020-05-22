package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressed()?.let {
            requireActivity().onBackPressedDispatcher.addCallback(object :
                OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    it.invoke()
                }
            })
        }
    }

    /**
     * 处理回退事件
     */
    open fun onBackPressed(): (() -> Unit)? {
        return null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDisposableManager.init(this)
        val root = inflateView(inflater, container)
        mDisplayDelegate.init(requireActivity(), createCommonPage(root))
        subscribeLiveData()
        return root
    }

    protected open fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(mLayoutId, container, false)
    }

    open fun subscribeLiveData() {
        val viewModel = getViewModel()
        if (viewModel is BaseViewModel) {
            observe(viewModel.tips) { mDisplayDelegate.showTips(it) }
            observe(viewModel.loading) {
                when (it) {
                    is LoadingState.Loading -> showLoading(it.message)
                    is LoadingState.Hide -> hideLoading()
                }
            }
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