package com.pretty.core

import android.app.Activity
import com.blankj.utilcode.util.ActivityUtils
import com.pretty.core.base.BaseApplication
import com.pretty.core.config.GlobalConfiguration
import kotlin.properties.Delegates

object Foundation {

    @JvmStatic
    private var INSTANCE: BaseApplication by Delegates.notNull()

    @JvmStatic
    fun init(context: BaseApplication) {
        INSTANCE = context
    }

    @JvmStatic
    fun getAppContext(): BaseApplication = INSTANCE

    @JvmStatic
    fun getTopActivity(): Activity = ActivityUtils.getTopActivity()


    @JvmStatic
    fun getGlobalConfig(): GlobalConfiguration = INSTANCE.getGlobalConfiguration()

}
