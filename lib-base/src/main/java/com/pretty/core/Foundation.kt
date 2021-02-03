package com.pretty.core

import android.app.Activity
import com.blankj.utilcode.util.ActivityUtils
import com.pretty.core.arch.state.StatePageManager
import com.pretty.core.base.BaseApplication
import com.pretty.core.config.GlobalConfiguration
import com.pretty.core.ext.initLogger
import com.pretty.core.util.AppSPUtil
import kotlin.properties.Delegates

object Foundation {

    @JvmStatic
    private var app: BaseApplication by Delegates.notNull()

    @JvmStatic
    fun init(context: BaseApplication) {
        app = context

        AppSPUtil.init()
        StatePageManager.initDefault(getGlobalConfig().statePageConfig)
        initLogger(BuildConfig.DEBUG, "timber", getGlobalConfig().releaseLogTree)
    }

    @JvmStatic
    fun getAppContext(): BaseApplication = app

    @JvmStatic
    fun getTopActivity(): Activity = ActivityUtils.getTopActivity()

    @JvmStatic
    fun getGlobalConfig(): GlobalConfiguration = app.mConfiguration

}
