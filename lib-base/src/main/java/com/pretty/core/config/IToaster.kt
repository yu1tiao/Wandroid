package com.pretty.core.config

import android.view.Gravity
import android.widget.Toast
import com.pretty.core.Foundation
import es.dmoral.toasty.Toasty

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 4/15/21
 * @description Toaster
 */
interface IToaster {
    fun show(style: ToastStyle, message: String?)
}

/**toast样式，适配多种风格*/
enum class ToastStyle {
    ERROR, SUCCESS, INFO, WARNING, NORMAL
}

val defaultToaster = object : IToaster {

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
