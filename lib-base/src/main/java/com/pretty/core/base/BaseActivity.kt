package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.pretty.core.Foundation
import com.pretty.core.arch.LoadingManager
import com.pretty.core.arch.LoadingState
import com.pretty.core.ext.observe


/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val mLayoutId: Int
    protected abstract val mViewModel: VM
    private var subscribed = false
    private val loadingManager by lazy { LoadingManager() }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams(savedInstanceState)
        val root = prepareContentView()
        afterSetContent(root)
        initPage(root)
        if (!subscribed) {
            subscribeLiveData()
            subscribed = true
        }
    }

    protected open fun initParams(savedInstanceState: Bundle?) {
    }

    protected open fun prepareContentView(): View {
        val view = LayoutInflater.from(this).inflate(mLayoutId, null)
        setContentView(view, ViewGroup.LayoutParams(-1, -1))
        return view
    }

    abstract fun initPage(contentView: View)

    protected open fun afterSetContent(root: View) {
        loadingManager.init(this, lifecycle, Foundation.getGlobalConfig().loadingFactory)
    }

    open fun subscribeLiveData() {
        subscribeLoading(mViewModel)
    }

    // 一个页面使用多个viewModel时，需要订阅其他viewModel的loading
    protected open fun subscribeLoading(viewModel: BaseViewModel) {
        observe(viewModel.loading) {
            when (it) {
                is LoadingState.Loading -> loadingManager.showLoading(it.message)
                is LoadingState.Hide -> loadingManager.dismissLoading()
            }
        }
    }

}