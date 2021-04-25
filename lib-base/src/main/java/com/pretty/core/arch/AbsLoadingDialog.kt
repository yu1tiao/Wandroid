package com.pretty.core.arch

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import com.pretty.core.R
import com.pretty.core.widget.LoadingView

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description LoadingDialog
 */
abstract class AbsLoadingDialog(context: Context, theme: Int) : Dialog(context, theme) {

    abstract fun show(message: String?)
    abstract fun updateMessage(message: String?)

    interface Factory {
        fun createDialog(context: Context): AbsLoadingDialog
    }
}

val defaultLoadingFactory = object : AbsLoadingDialog.Factory {
    override fun createDialog(context: Context): AbsLoadingDialog {
        return DefaultLoadingDialog(context, R.style.LightDialog)
    }
}

class DefaultLoadingDialog(context: Context, theme: Int) : AbsLoadingDialog(context, theme) {

    init {
        FrameLayout(context).apply {
            setBackgroundColor(Color.TRANSPARENT)
            layoutParams = ViewGroup.LayoutParams(-2, -2)
            val loadingImage = LoadingView(context)
            addView(loadingImage, FrameLayout.LayoutParams(60, 60))
        }.let {
            setContentView(it)
        }
    }

    override fun show(message: String?) {
        super.show()
    }

    override fun updateMessage(message: String?) {
        // do nothing
    }

}