package com.pretty.core.arch

import android.content.Context
import com.pretty.core.util.showToast
import com.pretty.core.widget.LoadingDialog

class DisplayDelegate : IDisplayDelegate {

    private var context: Context? = null
    private var loadingDialog: LoadingDialog? = null

    override fun init(context: Context) {
        this.context = context
    }

    override fun showLoading(msg: String?, needDimBehind: Boolean, cancelCallback: (() -> Unit)?) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.create(getContext())
        if (loadingDialog?.isShowing == true && !msg.isNullOrEmpty())
            loadingDialog?.updateMessage(msg)
        else
            loadingDialog?.show(msg)
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun updateLoadingMsg(msg: String) {
        loadingDialog?.updateMessage(msg)
    }

    override fun showTips(text: String?) {
        showToast(text.toString())
    }

    override fun getContext(): Context {
        return context ?: throw NullPointerException("can not get context after destroy")
    }

    override fun onDestroy() {
        context = null
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }
}