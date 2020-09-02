package com.pretty.core.base

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.pretty.core.R

abstract class BaseDialog(context: Context) : Dialog(context) {

    open fun layoutBottom() {
        window?.run {
            decorView.setPadding(0, 0, 0, 0)
            val lp = attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.dimAmount = 0.5f
            lp.gravity = Gravity.BOTTOM

            this.attributes = lp
            this.setWindowAnimations(R.style.bottom_in_bottom_out)
        }
    }

}