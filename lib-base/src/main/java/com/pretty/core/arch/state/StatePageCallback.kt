package com.pretty.core.arch.state

import android.view.View

interface StatePageCallback {

    fun onEmptyCreated(
        parent: StatePage,
        view: View,
        retryIds: List<Int>?,
        retryTask: (() -> Unit)?
    )

    fun onErrorCreated(
        parent: StatePage,
        view: View,
        retryIds: List<Int>?,
        retryTask: (() -> Unit)?
    )

    fun onLoadingCreated(parent: StatePage, view: View)

    /**
     * 显示view的时候更新空布局或错误布局的文字图标
     */
    fun updateEmptyView(parent: StatePage, iconRes: Int?, text: String?)

    fun updateErrorView(parent: StatePage, iconRes: Int?, text: String?)

    fun updateLoadingMessage(parent: StatePage, loadingText: String?)

}