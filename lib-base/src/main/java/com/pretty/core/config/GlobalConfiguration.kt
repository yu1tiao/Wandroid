package com.pretty.core.config

import android.widget.Toast
import com.pretty.core.Foundation
import com.pretty.core.arch.commonpage.CommonPageFactory
import com.pretty.core.util.CrashLogReporter
import okhttp3.OkHttpClient
import retrofit2.Retrofit


interface ConfigurationProvider {
    fun getGlobalConfiguration(): GlobalConfiguration
}

/** 全局配置 */
class GlobalConfiguration(
    var netPolicyProvider: NetPolicyProvider? = Foundation.getAppContext(), // 网络策略，默认使用BaseApplication实现
    var okHttpConfigCallback: HttpClientConfigCallback? = null,// 配置OkHttp
    var retrofitConfigCallback: RetrofitConfigCallback? = null,// 配置retrofit
    var toastFactory: ToastFactory = DefaultToastFactory(),     // 全局的Toast工厂，自定义toast
    var errorHandler: ErrorHandler = toastErrorHandler,     // 框架错误回调(网络请求，包括协程、rxjava等，默认弹出toast)
    var commonPageFactory: CommonPageFactory = DefaultCommonPageFactory(),// 全局通用布局(包括空页面、加载中、错误等状态，BaseActivity中可以选择是否注入或者自定义)
    var crashLogReporter: CrashLogReporter = FakeCrashLogReporter() // Timber日志上报
) {

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
