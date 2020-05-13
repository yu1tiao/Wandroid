package com.pretty.core.ext

import android.content.Context
import android.content.res.Resources
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author mathew
 * @date 2020/1/8
 */

fun View.throttleClick(seconds: Long = 1000, onNext: ((View) -> Unit)) {
    this.setOnClickListener(ThrottleClickListener(seconds, onNext))
}

class ThrottleClickListener(private val timeout: Long, private val onNext: (View) -> Unit) :
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

fun dp2px(dpValue: Int): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return (dpValue * scale + 0.5f)
}

/**
 * 所有输入框都有值时设置按钮可用
 * @param ext 可选的其他判断，也要为true按钮才可用
 */
fun View.disableIfNoInput(vararg edittext: EditText, ext: (() -> Boolean)? = null): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            var btnEnable = true
            val extEnable = ext?.invoke() ?: true
            if (extEnable) {
                edittext.forEach {
                    if (it.text.isNullOrEmpty()) {
                        btnEnable = false
                        return@forEach
                    }
                }
            } else {
                btnEnable = false
            }
            isEnabled = btnEnable
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
    edittext.forEach {
        it.addTextChangedListener(textWatcher)
    }
    return textWatcher
}