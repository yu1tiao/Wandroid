package com.pretty.core.config


interface IToaster {
    fun show(style: ToastStyle, message: String?)
}

/**toast样式，适配多种风格*/
enum class ToastStyle {
    ERROR, SUCCESS, INFO, WARNING, NORMAL
}
