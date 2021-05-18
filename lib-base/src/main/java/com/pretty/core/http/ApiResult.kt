package com.pretty.core.http

import java.io.Serializable


/**
 * 数据层的数据转换为ApiResult返回给ViewModel层
 */
class ApiResult<T>(val value: Any?) : Serializable {

    val isSuccess: Boolean get() = value !is Failure
    val isFailure: Boolean get() = value is Failure

    fun getOrNull(): T? = when {
        isFailure -> null
        else -> value as T?
    }

    fun getOrDefault(defaultValue: T): T = when {
        isFailure -> defaultValue
        else -> (value as T?) ?: defaultValue
    }

    fun exceptionOrNull(): Throwable? = when (value) {
        is Failure -> value.throwable
        else -> null
    }

    fun <R> map(transform: (T?) -> R?): ApiResult<R> = when {
        isFailure -> error((value as Failure).throwable)
        else -> success(transform(value as T?))
    }

    fun onSuccess(callback: (T?) -> Unit): ApiResult<T> {
        if (isSuccess) {
            callback.invoke(value as T?)
        }
        return this
    }

    fun onError(callback: (Throwable) -> Unit): ApiResult<T> {
        if (isFailure) {
            (value as Failure?)?.throwable?.let(callback)
        }
        return this
    }

    companion object {
        fun <T> success(data: T?): ApiResult<T> = ApiResult(data)
        fun <T> error(throwable: Throwable): ApiResult<T> = ApiResult(Failure(throwable))
    }

    data class Failure(val throwable: Throwable) : Serializable

}
