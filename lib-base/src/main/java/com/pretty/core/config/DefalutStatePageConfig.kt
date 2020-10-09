package com.pretty.core.config

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.pretty.core.R
import com.pretty.core.arch.state.StatePage
import com.pretty.core.arch.state.StatePageCallback
import com.pretty.core.arch.state.StatePageConfig


val globalStateConfig = StatePageConfig(
    R.layout.l_common_page_loading,
    R.layout.l_common_page_empty,
    R.layout.l_common_page_error,
    listOf(R.id.state_page_retry_id),
    DefaultStatePageCallback()
)

class DefaultStatePageCallback : StatePageCallback {

    private var emptyIcon: ImageView? = null
    private var errorIcon: ImageView? = null
    private var emptyMessage: TextView? = null
    private var errorMessage: TextView? = null

    override fun onEmptyCreated(
        parent: StatePage,
        view: View,
        retryIds: List<Int>?,
        retryTask: (() -> Unit)?
    ) {
        retryIds?.forEach {
            view.findViewById<View>(it).setOnClickListener {
                retryTask?.invoke()
            }
        }
        emptyIcon = view.findViewById(R.id.iv_icon)
        emptyMessage = view.findViewById(R.id.tv_message)
    }

    override fun onErrorCreated(
        parent: StatePage,
        view: View,
        retryIds: List<Int>?,
        retryTask: (() -> Unit)?
    ) {
        retryIds?.forEach {
            view.findViewById<View>(it).setOnClickListener {
                retryTask?.invoke()
            }
        }
        errorIcon = view.findViewById(R.id.iv_icon)
        errorMessage = view.findViewById(R.id.tv_message)
    }

    override fun onLoadingCreated(parent: StatePage, view: View) {

    }

    override fun updateEmptyView(parent: StatePage, iconRes: Int?, text: String?) {
        iconRes?.let {
            emptyIcon?.setImageResource(it)
        }
        text?.let {
            emptyMessage?.text = it
        }
    }

    override fun updateErrorView(parent: StatePage, iconRes: Int?, text: String?) {
        iconRes?.let {
            errorIcon?.setImageResource(it)
        }
        text?.let {
            errorMessage?.text = it
        }
    }

    override fun updateLoadingMessage(parent: StatePage, loadingText: String?) {

    }
}