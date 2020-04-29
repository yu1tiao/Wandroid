package com.pretty.core.arch.commonpage

import android.view.View
import com.pretty.core.arch.Destroyable

interface ICommonPage : Destroyable {
    fun showLoading(hideContentWhenLoading: Boolean = false, loadingText: String? = null)
    fun showError(iconRes: Int = -1, errorText: String? = null)
    fun showEmpty(iconRes: Int = -1, emptyText: String? = null)
    fun showSuccess()

    fun dismissLoading()
    fun updateLoadingMessage(loadingText: String)

    fun withRetry(task: () -> Unit): ICommonPage

    companion object {
        const val STATUS_LOADING = 1
        const val STATUS_SUCCESS = 2
        const val STATUS_ERROR = 3
        const val STATUS_EMPTY = 4
    }
}

interface CommonPageFactory {
    /**
     * 创建不同状态的view
     */
    fun createEmptyView(holder: CommonPage, retryTask: (() -> Unit)?): View
    fun createErrorView(holder: CommonPage, retryTask: (() -> Unit)?): View
    fun createLoadingView(holder: CommonPage): View

    /**
     * 显示view的时候更新空布局或错误布局的文字图标
     */
    fun updateEmptyView(holder: CommonPage, iconRes: Int?, text: String?)
    fun updateErrorView(holder: CommonPage, iconRes: Int?, text: String?)
    fun updateLoadingMessage(loadingText: String?)
}