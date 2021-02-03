package com.pretty.core.util

import android.os.Handler
import android.os.Looper

val mainHandler = Handler(Looper.getMainLooper())

fun runOnMainThread(delay: Long = 0L, block: () -> Unit) {
    if (delay > 0) {
        mainHandler.postDelayed(block, delay)
    } else {
        if (isMainThread()) {
            block()
        } else {
            mainHandler.post(block)
        }
    }
}

fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}