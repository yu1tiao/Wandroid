package com.pretty.core.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import com.pretty.core.R
import kotlinx.android.synthetic.main.d_loading_dialog.*

/**
 * 加载中转圈的弹窗
 */
class LoadingDialog private constructor(context: Context, theme: Int) : Dialog(context, theme) {

    init {
        setContentView(R.layout.d_loading_dialog)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window!!.attributes.gravity = Gravity.CENTER
        val lp = window!!.attributes
        lp.dimAmount = 0.2f
        window!!.attributes = lp
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