package com.pretty.core.arch

import androidx.annotation.MainThread

/**
 * @author yu
 * @date 2018/10/26
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
