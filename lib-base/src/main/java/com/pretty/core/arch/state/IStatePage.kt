package com.pretty.core.arch.state

interface IStatePage {
    fun showLoading()
    fun showError()
    fun showEmpty()
    fun showContent()
}