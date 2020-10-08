package com.pretty.core.config

import android.view.Gravity
import android.widget.Toast
import com.pretty.core.Foundation
import es.dmoral.toasty.Toasty

class DefaultToastFactory : ToastFactory {

    init {
        Toasty.Config.getInstance()
            .allowQueue(false)
            .apply()
    }

    override fun invoke(type: ToastStyle, message: CharSequence, duration: Int): Toast {
        // 根据不同类型来构造不同样式的toast
        val context = Foundation.getAppContext()
        return when (type) {
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