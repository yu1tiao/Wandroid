package com.pretty.core.arch.state

import android.view.View

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description StatePageCallback
 */

interface StatePageCallback {
    fun onEmptyCreated(parent: StatePage, view: View)
    fun onErrorCreated(parent: StatePage, view: View)
    fun onLoadingCreated(parent: StatePage, view: View)
    fun onStatusChange(parent: StatePage, newStatus: Status, oldStatus: Status)
}