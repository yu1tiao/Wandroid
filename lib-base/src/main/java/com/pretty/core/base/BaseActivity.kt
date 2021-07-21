package com.pretty.core.base

import android.os.Bundle
import android.view.View
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

    protected abstract val mViewModel: VM
    private val loadingManager by lazy { LoadingManager() }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(prepareContentView())

        loadingManager.init(this, lifecycle, Foundation.getGlobalConfig().loadingFactory)
        subscribeLoading(mViewModel)

        initView()
        initObserver()
        initData()
    }

    abstract fun prepareContentView(): View

    abstract fun initView()

    abstract fun initObserver()

    abstract fun initData()

    fun showLoading(message: String? = null) {
        loadingManager.showLoading(message)
    }

    fun hideLoading() {
        loadingManager.dismissLoading()
    }

    // 一个页面使用多个viewModel时，需要订阅其他viewModel的loading
    protected open fun subscribeLoading(viewModel: BaseViewModel) {
        observe(viewModel.loading) {
            when (it) {
                is LoadingState.Loading -> showLoading(it.message)
                is LoadingState.Hide -> hideLoading()
            }
        }
    }

}