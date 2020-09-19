package com.pretty.core.config


interface NetPolicyProvider {
    fun getNetPolicy(buildType: String): INetPolicy
}

/**
 * 不同环境的网络配置策略
 */
interface INetPolicy {
    companion object {
        const val DEV = "开发环境"
        const val QA = "测试环境"
        const val RELEASE = "生产环境"
    }

    /**
     * 策略名字
     */
    fun getPolicyName(): String

    /**
     * 获取设置的API地址
     */
    fun getApiBaseUrl(): String

    /**
     * 获取默认H5 host
     */
    fun getWebHostUrl(): String
}
