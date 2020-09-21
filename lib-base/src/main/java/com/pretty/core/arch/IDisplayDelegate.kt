package com.pretty.core.arch

import android.content.Context
import com.pretty.core.arch.state.IStatePage
import com.pretty.core.arch.state.StatePage

interface IDisplayDelegate : Destroyable, IStatePage {

    fun init(context: Context, statePage: StatePage? = null)

    fun showTips(text: String?)

    fun getContext(): Context
}