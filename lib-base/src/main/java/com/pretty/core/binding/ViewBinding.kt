package com.pretty.core.binding

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pretty.core.ext.throttleClick

/**
 * @author yu
 * @date 2018/10/26
 */
interface ViewClickConsumer : (View) -> Unit

const val DEFAULT_THROTTLE_TIME = 500L

/**
 * [View]是否可见
 *
 * @param visible 值为true时可见
 */
@BindingAdapter("bind_visible_gone")
fun setVisibleGone(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * [View]长按事件
 *
 * @param consumer 事件消费者
 */
@SuppressLint("CheckResult")
@BindingAdapter("bind_onLongClick")
fun setOnLongClickEvent(view: View, consumer: ViewClickConsumer) {
    view.setOnLongClickListener {
        consumer(it)
        true
    }
}

/**
 * [View]防抖动点击事件
 *
 * @param consumer 点击事件消费者
 * @param time 防抖动时间间隔，单位ms
 */
@SuppressLint("CheckResult")
@BindingAdapter("bind_onClick")
fun setOnClickEvent(view: View, consumer: ViewClickConsumer) {
    view.throttleClick(DEFAULT_THROTTLE_TIME, consumer)
}

@SuppressLint("CheckResult")
@BindingAdapter("bind_fab_background_tint")
fun fabBackgroundTint(view: FloatingActionButton, colorStateList: ColorStateList) {
    view.backgroundTintList = colorStateList
}