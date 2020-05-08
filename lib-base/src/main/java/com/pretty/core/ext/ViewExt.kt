package com.pretty.core.ext

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager

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

/**
 * 弹出软键盘
 */
fun View.showSoftInput() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * 隐藏软键盘
 */
fun View.hideSoftInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}