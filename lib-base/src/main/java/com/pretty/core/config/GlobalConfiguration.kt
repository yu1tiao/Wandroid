package com.pretty.core.config

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.pretty.core.Foundation
import com.pretty.core.R
import com.pretty.core.arch.AbsLoadingDialog
import com.pretty.core.arch.DefaultLoadingDialog
import com.pretty.core.arch.state.StatePageConfig
import es.dmoral.toasty.Toasty

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
        init {
            Toasty.Config.getInstance()
                .allowQueue(false)
                .apply()
        }

        override fun show(style: ToastStyle, message: String?) {
            if (message.isNullOrEmpty()) return
            val duration = if (message.length > 10) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            val context = Foundation.getAppContext()
            when (style) {
                ToastStyle.INFO -> Toasty.info(context, message, duration, true)
                ToastStyle.SUCCESS -> Toasty.success(context, message, duration, true)
                ToastStyle.WARNING -> Toasty.warning(context, message, duration, true)
                ToastStyle.ERROR -> Toasty.error(context, message, duration, true)
                else -> Toasty.normal(context, message, duration)
            }.apply {
                setGravity(Gravity.CENTER, 0, 0)
            }
        }
    }

    var logger: ILogger = object : ILogger {

        override var globalTag: String = "HxLogger"
        override var isDebug: Boolean = true

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
        fun create(initializer: GlobalConfiguration.() -> Unit): GlobalConfiguration {
            return GlobalConfiguration().apply {
                initializer()
            }
        }
    }
}