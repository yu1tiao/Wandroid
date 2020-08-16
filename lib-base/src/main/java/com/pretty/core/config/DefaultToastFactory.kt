package com.pretty.core.config

import android.view.Gravity
import android.widget.Toast
import com.pretty.core.Foundation

class DefaultToastFactory : ToastFactory {
    override fun invoke(type: Int, message: CharSequence, duration: Int): Toast {
//        根据不同类型来构造不同样式的toast
//        val toast = when (type) {
//            ToastType.SUCCESS ->
//            ToastType.FAIL->
//            else ->
//        }
        return Toast.makeText(Foundation.getAppContext(), message, duration)
            .apply { setGravity(Gravity.CENTER, 0, 0) }
    }
}