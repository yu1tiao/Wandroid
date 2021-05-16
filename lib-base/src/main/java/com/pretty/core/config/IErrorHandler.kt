package com.pretty.core.config

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.pretty.core.ext.showToast
import com.pretty.core.http.ApiException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


typealias IErrorHandler = (throwable: Throwable) -> Unit

val toastErrorHandler = object : IErrorHandler {
    override fun invoke(throwable: Throwable) {
        val msg = when (throwable) {
            is ApiException -> throwable.message
            is ConnectException -> "网络连接错误，请稍后再试"
            is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> "数据解析错误，请稍后再试"
            is UnknownHostException, is SocketTimeoutException -> "网络请求超时，请稍后再试"
            is HttpException -> "服务器繁忙，请稍后再试"
            else -> "出错了,请稍后再试"
        }
        showToast(msg)
    }
}

val ignoreErrorHandler = object : IErrorHandler {
    override fun invoke(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
