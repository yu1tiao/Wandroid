package com.pretty.core.arch

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description LoadingManager
 */
class LoadingManager : LifecycleObserver {

    private var context: Context? = null
    private var loadingDialog: AbsLoadingDialog? = null
    private lateinit var dialogFactory: AbsLoadingDialog.Factory
    private lateinit var lifecycle: Lifecycle

    fun init(context: Context, lifecycle: Lifecycle, dialogFactory: AbsLoadingDialog.Factory) {
        this.context = context
        this.dialogFactory = dialogFactory
        this.lifecycle = lifecycle

        lifecycle.addObserver(this)
    }

    fun showLoading(message: String?) {
        if (loadingDialog == null)
            loadingDialog = dialogFactory.createDialog(
                context ?: throw NullPointerException("can not get context after destroy")
            )

        if (loadingDialog?.isShowing == true && !message.isNullOrEmpty())
            loadingDialog?.updateMessage(message)
        else
            loadingDialog?.show(message)
    }

    fun dismissLoading() {
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        context = null
        dismissLoading()
        loadingDialog = null
    }
}