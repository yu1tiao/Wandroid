package com.pretty.core.arch.state

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description StatePageConfig
 */

class StatePageConfig(
    @LayoutRes var loadingLayout: Int,
    @LayoutRes var emptyLayout: Int,
    @LayoutRes var errorLayout: Int,
    @IdRes var emptyRetryIds: Array<Int> = emptyArray(),
    @IdRes var errorRetryIds: Array<Int> = emptyArray(),
    var callback: StatePageCallback? = null
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
                this.emptyRetryIds,
                this.errorRetryIds,
                this.callback
            )
        )
    }

}