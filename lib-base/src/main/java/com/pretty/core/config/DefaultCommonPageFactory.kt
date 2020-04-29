package com.pretty.core.config

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.StringUtils
import com.pretty.core.R
import com.pretty.core.arch.commonpage.CommonPage
import com.pretty.core.arch.commonpage.CommonPageFactory


/**
 * 默认的CommonPage工厂
 */
class DefaultCommonPageFactory : CommonPageFactory {
    private var emptyIcon: ImageView? = null
    private var errorIcon: ImageView? = null
    private var emptyText: TextView? = null
    private var errorText: TextView? = null
    private var loadingTextView: TextView? = null

    override fun createEmptyView(holder: CommonPage, retryTask: (() -> Unit)?): View {
        return LayoutInflater.from(holder.context)
            .inflate(R.layout.l_common_page_empty, holder.wrapper, false)
            .also {
                emptyIcon = it.findViewById(R.id.iv_icon)
                emptyText = it.findViewById(R.id.tv_message)
                it.setOnClickListener {
                    retryTask?.invoke()
                }
            }
    }

    override fun createErrorView(holder: CommonPage, retryTask: (() -> Unit)?): View {
        return LayoutInflater.from(holder.context)
            .inflate(R.layout.l_common_page_error, holder.wrapper, false)
            .also {
                errorIcon = it.findViewById(R.id.iv_icon)
                errorText = it.findViewById(R.id.tv_message)
                it.setOnClickListener {
                    retryTask?.invoke()
                }
            }
    }

    override fun createLoadingView(holder: CommonPage): View {
        return LayoutInflater.from(holder.context)
            .inflate(R.layout.l_common_page_loading, holder.wrapper, false)
            .also {
                loadingTextView = it.findViewById(R.id.tvMessage)
            }
    }

    override fun updateEmptyView(holder: CommonPage, iconRes: Int?, text: String?) {
        if (iconRes != null && iconRes > 0) {
            emptyIcon?.setImageResource(iconRes)
        } else {
            emptyIcon?.setImageResource(R.mipmap.ic_common_page_empty_icon)
        }
        if (text.isNullOrEmpty()) {
            emptyText?.text = StringUtils.getString(R.string.empty_default_message)
        } else {
            emptyText?.text = text
        }
    }

    override fun updateErrorView(holder: CommonPage, iconRes: Int?, text: String?) {
        if (iconRes != null && iconRes > 0) {
            errorIcon?.setImageResource(iconRes)
        } else {
            errorIcon?.setImageResource(R.mipmap.ic_common_page_error_icon)
        }
        if (text.isNullOrEmpty()) {
            errorText?.text = StringUtils.getString(R.string.error_default_message)
        } else {
            errorText?.text = text
        }
    }

    override fun updateLoadingMessage(loadingText: String?) {
        if (loadingText.isNullOrEmpty()) {
            loadingTextView?.text = StringUtils.getString(R.string.loading_default_message)
        } else {
            loadingTextView?.text = loadingText
        }
    }
}