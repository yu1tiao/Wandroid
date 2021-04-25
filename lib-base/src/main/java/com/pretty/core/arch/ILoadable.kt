package com.pretty.core.arch

import androidx.annotation.MainThread

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/4/10
 * @description ILoadable
 */
interface ILoadable {
    @MainThread
    fun showLoading(message: String? = null)

    @MainThread
    fun hideLoading()
}

sealed class LoadingState(var message: String? = null) {

    companion object {
        fun loading(message: String? = null): LoadingState {
            return Loading(message)
        }

        fun hide(): LoadingState {
            return Hide()
        }
    }

    class Loading(message: String? = null) : LoadingState(message)
    class Hide : LoadingState()
}
