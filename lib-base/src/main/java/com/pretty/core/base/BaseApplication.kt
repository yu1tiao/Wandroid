package com.pretty.core.base

import androidx.multidex.MultiDexApplication
import com.pretty.core.Foundation
import com.pretty.core.arch.commonpage.CommonPageManager
import com.pretty.core.config.ConfigurationProvider
import com.pretty.core.config.GlobalConfiguration
import com.pretty.core.config.NetPolicyProvider

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
        CommonPageManager.initDefault(configuration.commonPageFactory)
    }

    override fun getGlobalConfiguration(): GlobalConfiguration {
        return this.configuration
    }
}