package com.pretty.core.http


/**
 * 数据层的数据转换为ApiResult返回给ViewModel层
 */
sealed class ApiResult<T> {

    companion object {
        fun <T> success(data: T?): Success<T> = Success(data)
        fun <T> error(throwable: Throwable): Error<T> = Error(throwable)
    }

    data class Success<T>(val data: T?) : ApiResult<T>()

    data class Error<T>(val throwable: Throwable) : ApiResult<T>()
}
