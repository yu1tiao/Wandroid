package com.pretty.core.config

import android.widget.Toast
import com.pretty.core.arch.IDisplayDelegate
import com.pretty.core.arch.state.StatePageConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import timber.log.Timber

/** 全局配置 */
class GlobalConfiguration {

    var netPolicyProvider: NetPolicyProvider? = null // 网络策略
    var okHttpConfigCallback: HttpClientConfigCallback? = null// 配置OkHttp
    var retrofitConfigCallback: RetrofitConfigCallback? = null// 配置retrofit
    var toastFactory: ToastFactory = DefaultToastFactory()     // 全局的Toast工厂，自定义toast
    var errorHandler: ErrorHandler = toastErrorHandler     // 框架错误回调(网络请求，包括协程、rxjava等，默认弹出toast)
    var displayDelegateFactory: DisplayDelegateFactory = DefaultDisplayDelegateFactory()
    var releaseLogTree: Timber.Tree = FakeLogTree() // Timber日志上报
    var statePageConfig: StatePageConfig = globalStateConfig // 全局的空、错误、加载中布局

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
/** 全局的错误处理 */
typealias ErrorHandler = (Throwable) -> Unit
/** 全局的Toast工厂 */
typealias ToastFactory = (ToastStyle, CharSequence, Int) -> Toast
/** 加载框 */
typealias DisplayDelegateFactory = () -> IDisplayDelegate

enum class ToastStyle {
    ERROR, SUCCESS, INFO, WARNING, NORMAL
}
