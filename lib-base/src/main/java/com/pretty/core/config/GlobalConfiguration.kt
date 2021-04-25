package com.pretty.core.config

import com.pretty.core.arch.AbsLoadingDialog
import com.pretty.core.arch.defaultLoadingFactory
import com.pretty.core.arch.state.StatePageConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/** 全局配置 */
class GlobalConfiguration {

    var netPolicyProvider: NetPolicyProvider? = null // 网络策略
    var okHttpConfigCallback: HttpClientConfigCallback? = null// 配置OkHttp
    var retrofitConfigCallback: RetrofitConfigCallback? = null// 配置retrofit

    var statePageConfig: StatePageConfig = globalStateConfig // 全局的空、错误、加载中布局
    var loadingFactory: AbsLoadingDialog.Factory = defaultLoadingFactory // 请求接口时转圈dialog工厂
    var toaster: IToaster = defaultToaster // toast
    var logger: ILogger = defaultLogger // logger
    var httpCondition: Condition = defaultCondition // 网络请求是否正确的配置, 如 code == 200
    var errorHandler: IErrorHandler = toastErrorHandler

    companion object {
        fun create(initializer: GlobalConfiguration.() -> Unit): GlobalConfiguration {
            return GlobalConfiguration().apply {
                initializer()
            }
        }
    }
}

/** 回调配置OkHttp */
typealias HttpClientConfigCallback = (OkHttpClient.Builder) -> OkHttpClient.Builder
/** 回调配置Retrofit */
typealias RetrofitConfigCallback = (Retrofit.Builder) -> Retrofit.Builder