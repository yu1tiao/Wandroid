package com.pretty.core.ext

import android.os.SystemClock
import android.view.View

/**
 * @author mathew
 * @date 2020/1/8
 */

fun View.throttleClick(seconds: Long = 1000, onNext: ((View) -> Unit)) {
    this.setOnClickListener(ThrottleClickListener(seconds, onNext))
}

private class ThrottleClickListener(private val timeout: Long, private val onNext: (View) -> Unit) :
    View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val diff = SystemClock.elapsedRealtime() - lastClickTime
        if (diff > timeout) {
            onNext(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }

    }
}