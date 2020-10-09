package com.pretty.core.arch

import android.content.Context
import com.pretty.core.arch.state.IStatePage
import com.pretty.core.arch.state.StatePage

interface IDisplayDelegate : Destroyable, IStatePage {
    fun init(context: Context, statePage: StatePage? = null)
    fun getContext(): Context
    fun showTips(text: String?)

    fun showLoadingDialog(message: String? = null)
    fun dismissLoadingDialog()
    fun updateLoadingMessage(loadingText: String)
}