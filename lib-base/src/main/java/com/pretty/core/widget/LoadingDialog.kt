package com.pretty.core.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.pretty.core.R

/**
 * 加载中转圈的弹窗
 */
class LoadingDialog private constructor(context: Context, theme: Int) : Dialog(context, theme) {

    private var tvMessage: TextView

    init {
        setContentView(R.layout.d_loading_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window!!.attributes.gravity = Gravity.CENTER
        val lp = window!!.attributes
        lp.dimAmount = 0.2f
        window!!.attributes = lp

        tvMessage = findViewById(R.id.tvMessage)
    }

    fun show(message: String? = null) {
        if (!message.isNullOrEmpty())
            tvMessage.text = message
        super.show()
    }

    fun updateMessage(message: String) {
        if (isShowing && message.isNotEmpty()) {
            tvMessage.text = message
        }
    }

    companion object {
        fun create(context: Context): LoadingDialog {
            return LoadingDialog(context, R.style.LightDialog)
        }
    }
}