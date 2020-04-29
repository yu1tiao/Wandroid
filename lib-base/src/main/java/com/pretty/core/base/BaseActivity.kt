package com.pretty.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), IView, ILoadable {

    protected lateinit var mBinding: B
    protected abstract val mLayoutId: Int
    override val mDisplayDelegate: IDisplayDelegate by lazy { DisplayDelegate() }
    override val mDisposableManager: IDisposableManager by lazy { DisposableManager() }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDisposableManager.init(this)
        mBinding = DataBindingUtil.setContentView<B>(this, mLayoutId)
            .apply { lifecycleOwner = this@BaseActivity }
        mDisplayDelegate.init(this, createCommonPage(mBinding.root))
        subscribeBasic(mBinding)
    }

    @CallSuper
    open fun subscribeBasic(binding: B) {
        val viewModel = getViewModel() ?: ViewModelProvider(this).get(BaseViewModel::class.java)
        if (viewModel is BaseViewModel) {
            viewModel.tips.observe(this@BaseActivity, Observer { mDisplayDelegate.showTips(it) })
            viewModel.loading.observe(this@BaseActivity, Observer {
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