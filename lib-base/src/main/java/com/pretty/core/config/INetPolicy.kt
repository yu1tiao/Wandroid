package com.pretty.core.config


interface NetPolicyProvider {
    fun getNetPolicy(buildType: String): INetPolicy
}

/**
 * 不同环境的网络配置策略
 */
interface INetPolicy {
    companion object {
        const val DEV = "DEV"
        const val SIT = "SIT"
        const val RELEASE = "RELEASE"
    }

    fun getPolicyName(): String

    fun getApiBaseUrl(): String
}
