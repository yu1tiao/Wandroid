package com.pretty.core

import android.app.Activity
import com.blankj.utilcode.util.ActivityUtils
import com.pretty.core.arch.state.StatePageManager
import com.pretty.core.base.BaseApplication
import com.pretty.core.config.GlobalConfiguration
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

object Foundation {

    @JvmStatic
    private var app: BaseApplication by Delegates.notNull()

    @JvmStatic
    fun init(context: BaseApplication) {
        app = context

        MMKV.initialize(context)
        getGlobalConfig().statePageConfig?.let {
            StatePageManager.initDefault(it)
        }
    }

    @JvmStatic
    fun getAppContext(): BaseApplication = app

    @JvmStatic
    fun getTopActivity(): Activity = ActivityUtils.getTopActivity()

    @JvmStatic
    fun getGlobalConfig(): GlobalConfiguration = app.mConfiguration

}
