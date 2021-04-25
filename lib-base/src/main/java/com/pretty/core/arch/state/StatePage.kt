package com.pretty.core.arch.state

import android.content.Context
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.pretty.core.ext.throttleClick

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description StatePage
 */

class StatePage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IStatePage {

    private val statusViews = ArrayMap<Status, View>()
    private var retryTask: ((id: Int) -> Unit)? = null

    var currentStatus = Status.CONTENT
        private set

    lateinit var config: StatePageConfig

    fun getStatusView(status: Status): View? {
        return statusViews[status]
    }

    /**
     * 更新StateConfig
     */
    fun updateConfig(action: StatePageConfig.() -> Unit) {
        action(config)
        statusViews.filter { it.key != Status.CONTENT }
            .forEach { removeView(it.value) }
        statusViews.clear()
    }

    /**
     * 重试
     */
    fun retry(retryTask: (id: Int) -> Unit): StatePage {
        this.retryTask = retryTask
        return this
    }

    override fun showLoading() {
        innerShow(Status.LOADING)
    }

    override fun showError() {
        innerShow(Status.ERROR)
    }

    override fun showEmpty() {
        innerShow(Status.EMPTY)
    }

    override fun showContent() {
        innerShow(Status.CONTENT)
    }

    private fun innerShow(status: Status) {
        if (currentStatus == status) {
            return
        }

        // 拿到当前状态的view，如果没有则创建了放入缓存，并添加到wrapper中
        if (statusViews[status] == null) {
            when (status) {
                Status.LOADING -> {
                    inflateLayout(config.loadingLayout).apply {
                        addView(this, ViewGroup.LayoutParams(-1, -1))
                        config.callback?.onLoadingCreated(this@StatePage, this)
                    }
                }
                Status.EMPTY -> {
                    inflateLayout(config.emptyLayout).apply {
                        addView(this, ViewGroup.LayoutParams(-1, -1))

                        if (config.emptyRetryIds.isNullOrEmpty()) {
                            this.throttleClick {
                                retryTask?.invoke(id)
                            }
                        } else {
                            config.emptyRetryIds.forEach { id ->
                                findViewById<View>(id).throttleClick {
                                    retryTask?.invoke(id)
                                }
                            }
                        }

                        config.callback?.onEmptyCreated(this@StatePage, this)
                    }
                }
                Status.ERROR -> {
                    inflateLayout(config.errorLayout).apply {
                        addView(this, ViewGroup.LayoutParams(-1, -1))

                        if (config.errorRetryIds.isNullOrEmpty()) {
                            this.throttleClick {
                                retryTask?.invoke(id)
                            }
                        } else {
                            config.errorRetryIds.forEach { id ->
                                findViewById<View>(id).throttleClick {
                                    retryTask?.invoke(id)
                                }
                            }
                        }

                        config.callback?.onErrorCreated(this@StatePage, this)
                    }
                }
                Status.CONTENT -> getChildAt(0)
            }.also {
                it.visibility = View.GONE
                statusViews[status] = it
            }
        }

        config.callback?.onStatusChange(this, status, currentStatus)

        statusViews[currentStatus]?.visibility = View.GONE
        statusViews[status]?.visibility = View.VISIBLE

        currentStatus = status
    }

    private fun inflateLayout(layoutId: Int): View {
        return LayoutInflater.from(context).inflate(layoutId, this, false)
    }

    override fun onDetachedFromWindow() {
        statusViews.clear()
        super.onDetachedFromWindow()
    }

}