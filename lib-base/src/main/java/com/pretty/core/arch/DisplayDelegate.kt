package com.pretty.core.arch

import android.content.Context
import com.pretty.core.arch.commonpage.ICommonPage
import com.pretty.core.util.showToast
import com.pretty.core.widget.LoadingDialog

class DisplayDelegate : IDisplayDelegate {

    private var context: Context? = null
    private var commonPage: ICommonPage? = null
    private var loadingDialog: LoadingDialog? = null

    override fun init(context: Context, commonPage: ICommonPage?) {
        this.context = context
        this.commonPage = commonPage
    }

    override fun showLoading(msg: String?, needDimBehind: Boolean, cancelCallback: (() -> Unit)?) {
        if (commonPage == null) {
            if (loadingDialog == null)
                loadingDialog = LoadingDialog.create(getContext())
            loadingDialog?.show(msg)
        } else {
            commonPage?.showLoading(false, msg)
        }
    }

    override fun dismissLoading() {
        if (commonPage == null) {
            loadingDialog?.dismiss()
        } else {
            commonPage?.dismissLoading()
        }
    }

    override fun updateLoadingMsg(msg: String) {
        if (commonPage == null) {
            loadingDialog?.updateMessage(msg)
        } else {
            commonPage?.updateLoadingMessage(msg)
        }
    }

    override fun showTips(text: String?) {
        showToast(text.toString())
    }

    override fun showSuccess() {
        commonPage?.showSuccess()
    }

    override fun showError(iconRes: Int?, errorText: String?) {
        commonPage?.showError(iconRes ?: -1, errorText)
    }

    override fun showEmpty(iconRes: Int?, emptyText: String?) {
        commonPage?.showEmpty(iconRes ?: -1, emptyText)
    }

    override fun getContext(): Context {
        return context ?: throw NullPointerException("can not get context after destroy")
    }

    override fun destroy() {
        context = null
        commonPage?.destroy()
        commonPage = null
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }
}