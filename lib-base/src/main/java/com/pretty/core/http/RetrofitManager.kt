package com.pretty.core.http

import com.pretty.core.Foundation
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
        if (mRetrofit == null)
            initRetrofit()
        return mRetrofit!!.create(clazz)
    }

    private fun initRetrofit() {
        val configuration = Foundation.getGlobalConfig()
        val client = OkHttpClient.Builder()
            .run {
                configuration.okHttpConfigCallback?.invoke(this) ?: this
            }
            .build()

        if (configuration.netPolicyProvider == null) {
            throw RuntimeException("u must set a default NetPolicyProvider at first!")
        }

        mRetrofit = Retrofit.Builder()
            .baseUrl(configuration.netPolicyProvider!!.getNetPolicy().getApiBaseUrl())
            .client(client)
            .run {
                configuration.retrofitConfigCallback?.invoke(this) ?: this
            }
            .build()
    }

    fun <T> obtainService(service: Class<T>): T {
        var retrofitService: T?
        synchronized(mRetrofitServiceCache) {
            retrofitService = mRetrofitServiceCache[service.name] as T
            if (retrofitService == null) {
                retrofitService = createApiService(service)
                mRetrofitServiceCache[service.name] = retrofitService!! as Any
            }
        }
        return retrofitService!!
    }
}
