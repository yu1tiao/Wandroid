package com.pretty.core.arch.state

import androidx.annotation.LayoutRes

/**
 * 配置
 */
class StatePageConfig(
    @LayoutRes var loadingLayout: Int,
    @LayoutRes var emptyLayout: Int,
    @LayoutRes var errorLayout: Int,
    var retryIds: List<Int>?,
    var callback: StatePageCallback?
) {

    /**
     * 拷贝自己的设置创建一个新的StateConfig
     */
    fun newConfig(action: StatePageConfig.() -> StatePageConfig): StatePageConfig {
        return action(
            StatePageConfig(
                this.loadingLayout,
                this.emptyLayout,
                this.errorLayout,
                this.retryIds,
                this.callback
            )
        )
    }

}