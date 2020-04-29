package com.pretty.core.arch.commonpage

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.pretty.core.arch.commonpage.ICommonPage.Companion.STATUS_EMPTY
import com.pretty.core.arch.commonpage.ICommonPage.Companion.STATUS_ERROR
import com.pretty.core.arch.commonpage.ICommonPage.Companion.STATUS_LOADING
import com.pretty.core.arch.commonpage.ICommonPage.Companion.STATUS_SUCCESS

class CommonPage internal constructor(
    private val factory: CommonPageFactory,
    val context: Context,
    val wrapper: ViewGroup
) : ICommonPage {
    private var curState = STATUS_SUCCESS
    private val statusViews = mutableMapOf<Int, View>()
    private var contentView: View? = wrapper.getChildAt(0)
    private var retryTask: (() -> Unit)? = null

    init {
        statusViews[STATUS_SUCCESS] = contentView!!
    }

    override fun withRetry(task: () -> Unit): ICommonPage {
        retryTask = task
        return this
    }

    /**
     * @param hideContentWhenLoading  loading时是否隐藏内容页面
     */
    override fun showLoading(hideContentWhenLoading: Boolean, loadingText: String?) {
        showLoadingStatus(
            STATUS_LOADING,
            hideContentWhenLoading,
            text = loadingText
        )
    }

    override fun showSuccess() {
        showLoadingStatus(STATUS_SUCCESS)
    }

    override fun showError(iconRes: Int, errorText: String?) {
        showLoadingStatus(STATUS_ERROR, iconRes = iconRes, text = errorText)
    }

    override fun showEmpty(iconRes: Int, emptyText: String?) {
        showLoadingStatus(STATUS_EMPTY, iconRes = iconRes, text = emptyText)
    }

    override fun dismissLoading() {
        statusViews[STATUS_LOADING]?.visibility = View.GONE
    }

    override fun updateLoadingMessage(loadingText: String) {
        if (curState == STATUS_LOADING) {
            factory.updateLoadingMessage(loadingText)
        }
    }

    private fun showLoadingStatus(
        status: Int,
        hideContentWhenLoading: Boolean = false,
        iconRes: Int = -1,
        text: String? = null
    ) {
        if (curState == status) {
            return
        }

        // 拿到当前状态的view，如果没有则创建了放入缓存，并添加到wrapper中
        if (statusViews[status] == null) {
            val lp = ViewGroup.LayoutParams(-1, -1)
            when (status) {
                STATUS_LOADING -> factory.createLoadingView(this)
                STATUS_EMPTY -> factory.createEmptyView(this, retryTask)
                STATUS_ERROR -> factory.createErrorView(this, retryTask)
                else -> null
            }?.also {
                wrapper.addView(it, lp)
                statusViews[status] = it
                it.visibility = View.GONE
            }
        }

        when (status) {
            STATUS_EMPTY -> factory.updateEmptyView(this, iconRes, text)
            STATUS_ERROR -> factory.updateErrorView(this, iconRes, text)
            STATUS_LOADING -> factory.updateLoadingMessage(text)
        }

        if (status == STATUS_LOADING && curState == STATUS_SUCCESS && !hideContentWhenLoading) {
            statusViews[curState]?.visibility = View.VISIBLE
        } else {
            statusViews[curState]?.visibility = View.GONE
        }

        statusViews[status]?.visibility = View.VISIBLE
        curState = status
    }

    override fun destroy() {
        statusViews.clear()
        contentView = null
    }
}