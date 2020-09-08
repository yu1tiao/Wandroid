package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.pretty.core.arch.*
import com.pretty.core.ext.observe


/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), IView {

    protected abstract val mLayoutId: Int
    protected abstract val mViewModel: VM
    private var subscribed = false

    /**
     *  提供通用UI操作的能力，如显示隐藏loading，显示Empty、Error页面等
     *  默认不注入空布局和错误布局，如需使用，需要修改{@see #injectCommonPage}的值
     *  如需修改默认的Empty、Error页面，参见 @see #createCommonPage
     */
    override val mDisplayDelegate: IDisplayDelegate by lazy { DisplayDelegate() }

    /**
     * 提供在页面销毁时自动清理的能力，防止内存泄露
     */
    override val mDisposableManager: IDisposableManager by lazy { DisposableManager() }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContent(savedInstanceState)
        val root = prepareContentView()
        afterSetContent(root)
        if (!subscribed) {
            // 防止多次订阅
            subscribeLiveData()
            subscribed = true
        }
        initPage()
    }

    abstract fun initPage()

    protected open fun beforeSetContent(savedInstanceState: Bundle?) {
        mDisposableManager.init(this)
    }

    protected open fun prepareContentView(): View {
        val view = LayoutInflater.from(this).inflate(mLayoutId, null)
        setContentView(view, ViewGroup.LayoutParams(-1, -1))
        return view
    }

    protected open fun afterSetContent(root: View) {
        mDisplayDelegate.init(this)
    }

    open fun subscribeLiveData() {
        subscribeLoading(mViewModel)
    }

    // 一个页面使用多个viewModel时，需要订阅其他viewModel的loading
    fun subscribeLoading(viewModel: BaseViewModel) {
        observe(viewModel.loading) {
            when (it) {
                is LoadingState.Loading -> mDisplayDelegate.showLoading(it.message)
                is LoadingState.Hide -> mDisplayDelegate.hideLoading()
            }
        }
    }

}