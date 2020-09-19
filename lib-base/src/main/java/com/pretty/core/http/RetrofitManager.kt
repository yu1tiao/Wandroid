package com.pretty.core.http

import com.pretty.core.BuildConfig
import com.pretty.core.Foundation
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author yu
 * @date 2018/10/29
 */
object RetrofitManager {

    private var mRetrofit: Retrofit? = null
    private val mRetrofitServiceCache = mutableMapOf<String, Any>()

    private fun <T> createApiService(clazz: Class<T>): T {
        if (mRetrofit == null) {
            initDefaultRetrofit()
        }
        return mRetrofit!!.create(clazz)
    }

    private fun initDefaultRetrofit() {
        val configuration = Foundation.getGlobalConfig()

        if (configuration.netPolicyProvider == null) {
            throw RuntimeException("u must set a default NetPolicyProvider at first!")
        }

        val client = generateHttpClient()
        val baseUrl =
            configuration.netPolicyProvider?.getNetPolicy(BuildConfig.BUILD_TYPE)?.getApiBaseUrl()?.toHttpUrlOrNull()

        buildRetrofit(baseUrl!!, client)
    }

    private fun buildRetrofit(baseUrl: HttpUrl, client: OkHttpClient) {
        val configuration = Foundation.getGlobalConfig()
        mRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .run {
                configuration.retrofitConfigCallback?.invoke(this) ?: this
            }
            .build()
    }

    private fun generateHttpClient(): OkHttpClient {
        val configuration = Foundation.getGlobalConfig()
        return OkHttpClient.Builder()
            .run {
                configuration.okHttpConfigCallback?.invoke(this) ?: this
            }
            .build()
    }

    /**
     * 切换base url
     */
    fun changeServer(baseUrl: HttpUrl) {
        buildRetrofit(baseUrl, generateHttpClient())
        synchronized(mRetrofitServiceCache) {
            mRetrofitServiceCache.clear()
        }
    }

    /**
     * 获取api
     */
    fun <T> obtainService(service: Class<T>): T {
        var apiService: T?
        synchronized(mRetrofitServiceCache) {
            apiService = mRetrofitServiceCache[service.name] as T
            if (apiService == null) {
                apiService = createApiService(service)
                mRetrofitServiceCache[service.name] = apiService!! as Any
            }
        }
        return apiService!!
    }
}
