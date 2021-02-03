package com.pretty.core.util

import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.pretty.core.Foundation
import com.pretty.core.config.ToastStyle
import java.lang.reflect.Field

fun showToast(message: String?, type: ToastStyle = ToastStyle.NORMAL) {
    message?.let {
        if (it.isNotBlank()) {
            val block = {
                val duration = if (it.length > 10) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                val toast = Foundation.getGlobalConfig().toastFactory.invoke(type, it, duration)
                ToastCompat.hook(toast)
                toast.show()
            }
            runOnMainThread {
                block()
            }
        }
    }
}

/**
 * 解决toast的BadTokenException
 * 参考：
 *  https://www.cnblogs.com/qcloud1001/p/8421356.html
 *  https://cloud.tencent.com/developer/article/1034223
 */
private object ToastCompat {
    private var sField_TN: Field? = null
    private var sField_TN_Handler: Field? = null

    init {
        try {
            sField_TN = Toast::class.java.getDeclaredField("mTN")
            sField_TN?.isAccessible = true
            sField_TN_Handler = sField_TN?.type?.getDeclaredField("mHandler")
            sField_TN_Handler?.isAccessible = true
        } catch (e: Exception) {
        }
    }

    fun hook(toast: Toast) {
        try {
            val tn = sField_TN?.get(toast)
            val preHandler = sField_TN_Handler?.get(tn) as Handler
            sField_TN_Handler?.set(tn, SafelyHandlerWrapper(preHandler))
        } catch (e: Exception) {
        }
    }
}

private class SafelyHandlerWrapper(private val impl: Handler) : Handler() {

    override fun dispatchMessage(msg: Message) {
        try {
            super.dispatchMessage(msg)
        } catch (e: Exception) {
        }
    }

    override fun handleMessage(msg: Message) {
        impl.handleMessage(msg)
    }
}