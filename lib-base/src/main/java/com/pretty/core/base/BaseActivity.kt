package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
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
abstract class BaseActivity : AppCompatActivity(), IView, ILoadable {

    protected abstract val mLayoutId: Int
    override val mDisplayDelegate: IDisplayDelegate by lazy { DisplayDelegate() }
    override val mDisposableManager: IDisposableManager by lazy { DisposableManager() }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContent(savedInstanceState)
        val root = prepareContentView()
        afterSetContent(root)
        subscribeBase()
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
        mDisplayDelegate.init(this, createCommonPage(root))
    }

    @CallSuper
    open fun subscribeBase() {
        val viewModel = getViewModel()
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

    override fun showLoading(message: String?) {
        mDisplayDelegate.showLoading(message)
    }

    override fun hideLoading() {
        mDisplayDelegate.dismissLoading()
    }

    abstract fun getViewModel(): ViewModel?

    override fun createCommonPage(contentView: View): ICommonPage? {
        // 返回null不会自动注入empty、loading、error三个布局
        if (injectCommonPage()) {
            return CommonPageManager.getDefault().wrap(contentView).withRetry { reTry() }
            // 如果需要自定义的empty、loading、error，调用以下方法，传入自己的CommonPageFactory
//            return CommonPageManager.with(customFactory).wrap(contentView).withRetry { reTry() }
        }
        return null
    }

    /**
     * 是否注入默认的CommonPage
     */
    protected open fun injectCommonPage(): Boolean = false

    /**
     * 注入CommonPage后点击，错误页面或空页面点击重试会回调这里
     */
    protected open fun reTry() {

    }

}