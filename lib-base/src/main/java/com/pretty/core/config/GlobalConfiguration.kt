package com.pretty.core.config

import android.widget.Toast
import com.pretty.core.arch.commonpage.CommonPageFactory
import com.pretty.core.util.AppSPUtil

import okhttp3.OkHttpClient
import retrofit2.Retrofit


interface ConfigurationProvider {
    fun getGlobalConfiguration(): GlobalConfiguration
}

/** 全局配置 */
class GlobalConfiguration(
    var netPolicyProvider: NetPolicyProvider? = null, // 网络策略
    var okHttpConfigCallback: HttpClientConfigCallback? = null,
    var retrofitConfigCallback: RetrofitConfigCallback? = null,
    var toastFactory: ToastFactory = DefaultToastFactory(),     // 全局的Toast工厂，自定义toast
    var errorHandler: ErrorHandler = toastErrorHandler,     // 框架ViewModel中RxJava发生错误的时候回调
    var commonPageFactory: CommonPageFactory = DefaultCommonPageFactory()
) {

    init {
        AppSPUtil.init()
    }

    companion object {
        fun create(initializer: GlobalConfiguration.() -> Unit): GlobalConfiguration {
            return GlobalConfiguration().apply(initializer)
        }
    }
}

/** 回调配置OkHttp */
typealias HttpClientConfigCallback = (OkHttpClient.Builder) -> OkHttpClient.Builder
/** 回调配置Retrofit */
typealias RetrofitConfigCallback = (Retrofit.Builder) -> Retrofit.Builder
/** 全局的错误处理 */
typealias ErrorHandler = (Throwable) -> Unit
/** 全局的Toast工厂 */
typealias ToastFactory = (CharSequence, Int) -> Toast
