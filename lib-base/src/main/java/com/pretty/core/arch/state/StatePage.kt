package com.pretty.core.arch.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArrayMap

class StatePage constructor(
    private val wrapper: ViewGroup,
    private var config: StatePageConfig
) : IStatePage {

    private val statusViews = ArrayMap<Status, View>()
    private val contentView: View
    private var retryTask: (() -> Unit)? = null

    var currentState = Status.CONTENT
        private set

    init {
        val childCount = wrapper.childCount
        require(childCount == 1) {
            "StatePage的父视图初始化时只能有1个子视图"
        }
        contentView = wrapper.getChildAt(0)
        statusViews[Status.CONTENT] = contentView
    }

    /**
     * 更新StateConfig
     */
    fun updateConfig(action: StatePageConfig.() -> Unit) {
        action(config)
        statusViews.filter { it.key != Status.CONTENT }
            .forEach { wrapper.removeView(it.value) }

        statusViews.clear()
    }

    /**
     * 重试
     */
    fun retry(retryTask: () -> Unit): StatePage {
        this.retryTask = retryTask

        statusViews[Status.ERROR]?.let {
            wrapper.removeView(it)
        }
        statusViews.remove(Status.ERROR)

        statusViews[Status.EMPTY]?.let {
            wrapper.removeView(it)
        }
        statusViews.remove(Status.EMPTY)

        return this
    }


    override fun showLoading(loadingMsg: String?) {
        innerShow(Status.LOADING, text = loadingMsg)
    }

    override fun showError(iconRes: Int?, errorText: String?) {
        innerShow(Status.ERROR, iconRes, errorText)
    }

    override fun showEmpty(iconRes: Int?, emptyText: String?) {
        innerShow(Status.EMPTY, iconRes, emptyText)
    }

    override fun showContent() {
        innerShow(Status.CONTENT)
    }

    private fun innerShow(status: Status, iconRes: Int? = null, text: String? = null) {
        if (currentState == status) {
            return
        }

        // 拿到当前状态的view，如果没有则创建了放入缓存，并添加到wrapper中
        if (statusViews[status] == null) {
            when (status) {
                Status.LOADING -> {
                    inflateLayout(wrapper, config.loadingLayout).apply {
                        wrapper.addView(this, ViewGroup.LayoutParams(-1, -1))
                        config.callback?.onLoadingCreated(this@StatePage, this)
                    }
                }
                Status.EMPTY -> {
                    inflateLayout(wrapper, config.emptyLayout).apply {
                        wrapper.addView(this, ViewGroup.LayoutParams(-1, -1))
                        config.callback?.onEmptyCreated(
                            this@StatePage,
                            this,
                            config.retryIds,
                            retryTask
                        )
                    }
                }
                Status.ERROR -> {
                    inflateLayout(wrapper, config.errorLayout).apply {
                        wrapper.addView(this, ViewGroup.LayoutParams(-1, -1))
                        config.callback?.onErrorCreated(
                            this@StatePage,
                            this,
                            config.retryIds,
                            retryTask
                        )
                    }
                }
                Status.CONTENT -> contentView
            }.also {
                it.visibility = View.GONE
                statusViews[status] = it
            }
        }

        when (status) {
            Status.EMPTY -> config.callback?.updateEmptyView(this, iconRes, text)
            Status.ERROR -> config.callback?.updateErrorView(this, iconRes, text)
            Status.LOADING -> config.callback?.updateLoadingMessage(this, text)
            Status.CONTENT -> {
            }
        }

        statusViews[currentState]?.visibility = View.GONE
        statusViews[status]?.visibility = View.VISIBLE

        currentState = status
    }

    private fun inflateLayout(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    fun onDestroy() {
        statusViews.clear()
    }

}