package com.pretty.core.base

import androidx.multidex.MultiDexApplication
import com.pretty.core.BuildConfig
import com.pretty.core.Foundation
import com.pretty.core.config.ConfigurationProvider
import com.pretty.core.config.GlobalConfiguration
import com.pretty.core.config.NetPolicyProvider
import com.pretty.core.util.AppSPUtil
import com.pretty.core.util.L
import com.pretty.eventbus.core.XBus

/**
 * @author yu
 * @date 2018/11/9
 */
abstract class BaseApplication : MultiDexApplication(), ConfigurationProvider, NetPolicyProvider {

    private lateinit var configuration: GlobalConfiguration

    protected abstract fun initGlobalConfiguration(): GlobalConfiguration

    override fun onCreate() {
        super.onCreate()
        Foundation.init(this)
        configuration = initGlobalConfiguration()

        L.init(BuildConfig.DEBUG, Foundation.getGlobalConfig().crashLogReporter)
        AppSPUtil.init()
        XBus.init()
    }

    override fun getGlobalConfiguration(): GlobalConfiguration {
        return this.configuration
    }
}