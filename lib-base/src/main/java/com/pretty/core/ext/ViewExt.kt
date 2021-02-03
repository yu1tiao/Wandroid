package com.pretty.core.ext

import android.content.Context
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pretty.core.R

/**
 * @author mathew
 * @date 2020/1/8
 */

fun View.throttleClick(seconds: Long = 800, onNext: ((View) -> Unit)) {
    this.setOnClickListener(ThrottleClickListener(seconds, onNext))
}

fun View.debounceClick(seconds: Long = 800, onNext: ((View) -> Unit)) {
    this.setOnClickListener(DebounceClickListener(seconds, onNext))
}

class ThrottleClickListener(private val timeout: Long = 800, private val onNext: (View) -> Unit) :
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

class DebounceClickListener(private val wait: Long = 800, private val onNext: ((View) -> Unit)) :
    View.OnClickListener {
    override fun onClick(v: View) {
        var action = (v.getTag(R.id.click_debounce_action) as? DebounceAction)
        if (action == null) {
            action = DebounceAction(v, onNext)
            v.setTag(R.id.click_debounce_action, action)
        } else {
            action.block = onNext
        }
        v.removeCallbacks(action)
        v.postDelayed(action, wait)
    }
}

private class DebounceAction(val view: View, var block: ((View) -> Unit)) : Runnable {
    override fun run() {
        if (view.isAttachedToWindow) {
            block(view)
        }
    }
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleGone(flag: Boolean) {
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.visibleOrInvisible(flag: Boolean) {
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.INVISIBLE
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

/**
 * 所有输入框都有值时设置按钮可用
 * @param ext 可选的其他判断，也要为true按钮才可用
 */
fun View.disableIfEmpty(vararg edittext: EditText, ext: (() -> Boolean)? = null): TextWatcher {
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