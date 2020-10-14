package com.pretty.core.arch.state

import android.view.View

interface StatePageCallback {
    fun onEmptyCreated(parent: StatePage, view: View)
    fun onErrorCreated(parent: StatePage, view: View)
    fun onLoadingCreated(parent: StatePage, view: View)
    fun onStatusChange(parent: StatePage, newStatus: Status, oldStatus: Status)
}