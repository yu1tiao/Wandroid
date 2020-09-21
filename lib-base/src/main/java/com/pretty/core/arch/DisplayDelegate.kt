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

    override fun showLoading(loadingMsg: String?) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.create(getContext())
        if (loadingDialog?.isShowing == true && !loadingMsg.isNullOrEmpty())
            loadingDialog?.updateMessage(loadingMsg)
        else
            loadingDialog?.show(loadingMsg)
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

    override fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    override fun updateLoadingMessage(loadingText: String) {
        if (statePage != null) {
            statePage!!.updateLoadingMessage(loadingText)
        } else {
            loadingDialog?.updateMessage(loadingText)
        }
    }

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
            loadingDialog = null
        }
    }
}