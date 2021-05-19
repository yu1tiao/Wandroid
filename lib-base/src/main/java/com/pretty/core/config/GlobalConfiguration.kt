package com.pretty.core.config

import android.app.Application
import android.content.Context
import android.util.Log
import com.hjq.toast.ToastUtils
import com.pretty.core.Foundation
import com.pretty.core.R
import com.pretty.core.arch.AbsLoadingDialog
import com.pretty.core.arch.DefaultLoadingDialog
import com.pretty.core.arch.state.StatePageConfig

/** 全局配置 */
class GlobalConfiguration {

    var netPolicyProvider: NetPolicyProvider? = null // 网络策略
    var okHttpConfigCallback: HttpClientConfigCallback? = null// 配置OkHttp
    var retrofitConfigCallback: RetrofitConfigCallback? = null// 配置retrofit

    // 全局的空、错误、加载中布局
    var statePageConfig: StatePageConfig = StatePageConfig(
        R.layout.l_common_page_loading,
        R.layout.l_common_page_empty,
        R.layout.l_common_page_error,
    )

    // 请求接口时转圈dialog工厂
    var loadingFactory: AbsLoadingDialog.Factory = object : AbsLoadingDialog.Factory {
        override fun createDialog(context: Context): AbsLoadingDialog {
            return DefaultLoadingDialog(context, R.style.LightDialog)
        }
    }

    var toaster: IToaster = object : IToaster {
        override fun init(context: Context) {
            ToastUtils.init(context as Application)
        }

        override fun show(style: ToastStyle, message: String?) {
            if (message.isNullOrEmpty()) return
            ToastUtils.show(message)
        }
    }

    var logger: ILogger = object : ILogger {

        override var globalTag: String = "HxLogger"
        override var isDebug: Boolean = true

        override fun init(context: Context) {

        }

        override fun print(level: Int, tag: String, message: String?) {
            if (!isDebug || message.isNullOrEmpty()) return
            when (level) {
                Log.VERBOSE -> Log.v(tag, message)
                Log.DEBUG -> Log.d(tag, message)
                Log.INFO -> Log.i(tag, message)
                Log.WARN -> Log.w(tag, message)
                Log.ERROR -> Log.e(tag, message)
                else -> {
                }
            }
        }
    }

    // 网络请求是否正确的配置, 如 code == 200
    var httpCondition: Condition = {
        this.isSuccessful()
    }

    // 错误处理，异常时弹窗toast
    var errorHandler: IErrorHandler = toastErrorHandler


    companion object {
        fun create(
            context: Context,
            initializer: GlobalConfiguration.() -> Unit
        ): GlobalConfiguration {
            return GlobalConfiguration().apply {
                initializer()

                toaster.init(context)
                logger.init(context)
            }
        }
    }
}