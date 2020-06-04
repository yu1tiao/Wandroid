package com.pretty.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.pretty.core.arch.*
import com.pretty.core.arch.commonpage.CommonPageManager
import com.pretty.core.arch.commonpage.ICommonPage
import com.pretty.core.ext.observe


/**
 * @author yu
 * @date 2018/10/29
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), IView, ILoadable {

    protected abstract val mLayoutId: Int
    protected abstract val mViewModel: VM

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
        subscribeLiveData()
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

    open fun subscribeLiveData() {
        observe(mViewModel.tips) { mDisplayDelegate.showTips(it) }
        observe(mViewModel.loading) {
            when (it) {
                is LoadingState.Loading -> showLoading(it.message)
                is LoadingState.Hide -> hideLoading()
            }
        }
    }

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