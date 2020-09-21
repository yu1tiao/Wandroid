package com.pretty.core.config

import android.widget.Toast
import androidx.annotation.IntDef
import com.pretty.core.Foundation
import com.pretty.core.arch.state.StatePageConfig
import com.pretty.core.arch.state.StatePageManager
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
    var crashLogReporter: CrashLogReporter = FakeCrashLogReporter(), // Timber日志上报
    var statePageConfig: StatePageConfig = globalStateConfig // 全局的空、错误、加载中布局
) {

    companion object {
        fun create(initializer: GlobalConfiguration.() -> Unit): GlobalConfiguration {
            return GlobalConfiguration().apply {
                StatePageManager.initDefault(statePageConfig)
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
typealias ToastFactory = (@ToastType Int, CharSequence, Int) -> Toast

// toast 类型
@Retention(AnnotationRetention.SOURCE)
@Target(allowedTargets = [AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER])
@IntDef(value = [ToastType.NORMAL, ToastType.SUCCESS, ToastType.FAIL])
annotation class ToastType {
    companion object {
        const val NORMAL = 0
        const val SUCCESS = 1
        const val FAIL = 2
    }
}
