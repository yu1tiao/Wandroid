package com.pretty.wandroid

import com.pretty.core.base.BaseApplication
import com.pretty.core.config.GlobalConfiguration
import com.pretty.core.config.INetPolicy

class App : BaseApplication() {
    override fun initGlobalConfiguration(): GlobalConfiguration {
        return GlobalConfiguration.create {

        }
    }

    override fun getNetPolicy(): INetPolicy {
        return DebugNetPolicy()
    }
}

private class DebugNetPolicy : INetPolicy {
    override fun getPolicyName(): String {
        return INetPolicy.DEV
    }

    override fun getApiBaseUrl(): String {
       return "https://github.com/"
    }

    override fun getWebHostUrl(): String {
        return "https://github.com/"
    }
}
