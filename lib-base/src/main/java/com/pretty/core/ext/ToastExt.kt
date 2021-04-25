package com.pretty.core.ext

import com.pretty.core.Foundation
import com.pretty.core.config.ToastStyle


fun showToast(message: String?, style: ToastStyle = ToastStyle.NORMAL) {
    Foundation.getGlobalConfig().toaster.show(style, message)
}

fun String?.showAsToast() {
    showToast(message = this)
}