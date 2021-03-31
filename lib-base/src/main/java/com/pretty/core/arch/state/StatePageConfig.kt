package com.pretty.core.arch.state

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * 配置
 */
const val INVALID_ID = -1

class StatePageConfig(
    @LayoutRes var loadingLayout: Int,
    @LayoutRes var emptyLayout: Int,
    @LayoutRes var errorLayout: Int,
    @IdRes var emptyRetryId: Int = INVALID_ID,
    @IdRes var errorRetryId: Int = INVALID_ID,
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
                this.emptyRetryId,
                this.errorRetryId,
                this.callback
            )
        )
    }

}