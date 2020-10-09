package com.pretty.core.arch

import android.content.Context
import com.pretty.core.arch.state.StatePage
import com.pretty.core.util.showToast
import com.pretty.core.widget.LoadingDialog

class DisplayDelegate : IDisplayDelegate {

    private var context: Context? = null
    private var loadingDialog: LoadingDialog? = null
    private var statePage: StatePage? = null

    override fun init(context: Context, statePage: StatePage?) {
        this.context = context
        this.statePage = statePage
    }

    /////////////////////////////// StatePage 页面级 //////////////////////////////
    override fun showLoading(loadingMsg: String?) {
        statePage?.showLoading(loadingMsg)
    }

    override fun showError(iconRes: Int?, errorText: String?) {
        statePage?.showError(iconRes, errorText)
    }

    override fun showEmpty(iconRes: Int?, emptyText: String?) {
        statePage?.showEmpty(iconRes, emptyText)
    }

    override fun showContent() {
        statePage?.showContent()
    }
    /////////////////////////////// StatePage 页面级 //////////////////////////////

    /////////////////////////////// loading dialog //////////////////////////////
    override fun showLoadingDialog(message: String?) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.create(getContext())
        if (loadingDialog?.isShowing == true && !message.isNullOrEmpty())
            loadingDialog?.updateMessage(message)
        else
            loadingDialog?.show(message)
    }

    override fun dismissLoadingDialog() {
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
        statePage?.onDestroy()
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        loadingDialog = null
    }
}