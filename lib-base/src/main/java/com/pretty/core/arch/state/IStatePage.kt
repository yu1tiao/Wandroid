package com.pretty.core.arch.state

interface IStatePage {
    fun showLoading(loadingMsg: String? = null)
    fun showError(iconRes: Int? = null, errorText: String? = null)
    fun showEmpty(iconRes: Int? = null, emptyText: String? = null)
    fun showContent()
}