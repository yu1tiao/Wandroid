package com.pretty.core.config

import com.pretty.core.BuildConfig
import com.pretty.core.exception.ApiException
import com.pretty.core.util.showToast
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ToastErrorHandler : ErrorHandler {

    override fun invoke(throwable: Throwable) {
        val msg = when (throwable) {
            is ApiException -> throwable.message
            is ConnectException -> "网络连接失败"
            is SocketTimeoutException -> "网络请求超时"
            is HttpException -> "服务器繁忙，请稍后再试"
            else -> "出错了,请稍后再试"
        }
        showToast(msg)
    }
}

class IgnoreErrorHandler : ErrorHandler {

    override fun invoke(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
    }
}

val toastErrorHandler = ToastErrorHandler()

val ignoreErrorHandler = IgnoreErrorHandler()