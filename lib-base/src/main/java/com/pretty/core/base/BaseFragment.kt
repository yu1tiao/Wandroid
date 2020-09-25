package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.pretty.core.arch.*
import com.pretty.core.arch.state.StatePage
import com.pretty.core.arch.state.StatePageManager
import com.pretty.core.ext.observe

/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment(), IView {

    protected abstract val mLayoutId: Int
    protected abstract val mViewModel: VM
    protected open var injectStatePage = false // 是否注入空布局、错误布局和加载布局
    override val mDisplayDelegate: IDisplayDelegate by lazy { DisplayDelegate() }
    override val mDisposableManager: IDisposableManager by lazy { DisposableManager() }
    private var subscribed = false

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
        mDisplayDelegate.init(requireActivity(), createStatePage(root))
        if (!subscribed) {
            // 防止多次订阅
            subscribeLiveData()
            subscribed = true
        }
        return root
    }

    protected open fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(mLayoutId, container, false)
    }


    /**
     * injectStatePage = true时，自动注入全局的StatePage
     */
    protected open fun createStatePage(view: View): StatePage? {
        if (injectStatePage) {
            return StatePageManager.getDefault().wrap(view).retry { retry() }
        }
        // 如果不想使用全局的StatePage
//        StatePageManager.with {
//            // copy全局设置，在这里修改自己的自定义设置就好了
//        }.wrap(view).retry { retry() }
        return null
    }

    /**
     * 错误或者空页面点击重试会回调这里
     */
    protected open fun retry() {

    }

    open fun subscribeLiveData() {
        subscribeLoading(mViewModel)
    }

    protected fun subscribeLoading(viewModel: BaseViewModel) {
        observe(viewModel.loading) {
            when (it) {
                is LoadingState.Loading -> mDisplayDelegate.showLoading(it.message)
                is LoadingState.Hide -> mDisplayDelegate.dismissLoading()
            }
        }
    }

}