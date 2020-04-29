package com.pretty.core.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pretty.core.arch.LoadingState


/**
 * @author yu
 * @date 2018/10/26
 */

/**
 * [View]是否可见，专用于绑定loading
 */
@BindingAdapter("bind_loading")
fun setLoadingVisibleGone(view: View, state: LoadingState?) {
    view.visibility = when (state) {
        is LoadingState.Loading -> View.VISIBLE
        else -> View.GONE
    }
}

/**
 * 绑定loading的消息
 */
@BindingAdapter("bind_loading_message")
fun setLoadingMessage(view: View, state: LoadingState?) {
    val empty = state?.message.isNullOrEmpty()
    view.visibility = if (empty) View.GONE else View.VISIBLE
    if (view is TextView) {
        view.text = if (empty) "" else state?.message
    }
}