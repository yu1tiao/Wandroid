package com.pretty.wandroid

import com.pretty.core.config.INetPolicy

class DevNetPolicy : INetPolicy {

    override fun getPolicyName(): String {
        return INetPolicy.DEV
    }

    override fun getApiBaseUrl(): String {
        return "https://www.wanandroid.com/"
    }
}

class SitNetPolicy : INetPolicy {
    override fun getPolicyName(): String {
        return INetPolicy.SIT
    }

    override fun getApiBaseUrl(): String {
        return "https://www.wanandroid.com/"
    }
}

class ReleaseNetPolicy : INetPolicy {
    override fun getPolicyName(): String {
        return INetPolicy.RELEASE
    }

    override fun getApiBaseUrl(): String {
        return "https://www.wanandroid.com/"
    }
}
