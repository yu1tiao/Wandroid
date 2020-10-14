package com.pretty.core.arch

import android.content.Context
import com.pretty.core.arch.state.StatePage
import com.pretty.core.util.showToast
import com.pretty.core.widget.LoadingDialog

class DisplayDelegate : IDisplayDelegate {

    private var context: Context? = null
    private var loadingDialog: LoadingDialog? = null

    override fun init(context: Context) {
        this.context = context
    }

    /////////////////////////////// loading dialog //////////////////////////////
    override fun showLoading(message: String?) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.create(getContext())
        if (loadingDialog?.isShowing == true && !message.isNullOrEmpty())
            loadingDialog?.updateMessage(message)
        else
            loadingDialog?.show(message)
    }

    override fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    override fun updateLoadingMessage(loadingText: String) {
        loadingDialog?.updateMessage(loadingText)
    }
    /////////////////////////////// loading dialog //////////////////////////////

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
        }
        loadingDialog = null
    }
}