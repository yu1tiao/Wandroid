package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.pretty.core.Foundation
import com.pretty.core.arch.*
import com.pretty.core.ext.observe

/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected abstract val mLayoutId: Int
    protected abstract val mViewModel: VM
    private val loadingManager by lazy { LoadingManager() }
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
        val root = inflateView(inflater, container)
        loadingManager.init(
            requireActivity(),
            lifecycle,
            Foundation.getGlobalConfig().loadingFactory
        )
        if (!subscribed) {
            subscribeLiveData()
            subscribed = true
        }
        return root
    }

    protected open fun inflateView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(mLayoutId, container, false)
    }

    open fun subscribeLiveData() {
        subscribeLoading(mViewModel)
    }

    protected open fun subscribeLoading(viewModel: BaseViewModel) {
        observe(viewModel.loading) {
            when (it) {
                is LoadingState.Loading -> loadingManager.showLoading(it.message)
                is LoadingState.Hide -> loadingManager.dismissLoading()
            }
        }
    }

}