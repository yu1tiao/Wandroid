package com.pretty.wandroid

import com.pretty.core.config.INetPolicy

class DevNetPolicy : INetPolicy {
    override fun getPolicyName(): String {
        return INetPolicy.DEV
    }

    override fun getApiBaseUrl(): String {
        return "https://www.wanandroid.com/"
    }

    override fun getWebHostUrl(): String {
        return "https://www.wanandroid.com/"
    }
}
