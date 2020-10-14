package com.pretty.core.arch

import android.content.Context

interface IDisplayDelegate : Destroyable {
    fun init(context: Context)
    fun getContext(): Context
    fun showTips(text: String?)

    fun showLoading(message: String? = null)
    fun dismissLoading()
    fun updateLoadingMessage(loadingText: String)
}