package com.pretty.core.ext

import com.pretty.core.config.ignoreErrorHandler
import com.pretty.core.config.toastErrorHandler
import com.pretty.core.http.Resp
import com.pretty.core.http.check
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * @author mathew
 * @date 2020/1/8
 */

suspend inline fun <T> safeApi(noinline req: suspend () -> Resp<T>): Result<T?> {
    return try {
        val data = req().check().data
        Result.success(data)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend inline fun <T> safeApiNotNull(noinline req: suspend () -> Resp<T>): Result<T> {
    return try {
        val data = req().check().data ?: throw NullPointerException("request data is null")
        Result.success(data)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

suspend inline fun <T> toFlow(noinline req: suspend () -> Resp<T>): Flow<T?> {
    return flow {
        val data = req().check().data
        emit(data)
    }
}

suspend inline fun <T> toFlowNotNull(noinline req: suspend () -> Resp<T>): Flow<T> {
    return flow {
        val data = req().check().data ?: throw NullPointerException("request data is null")
        emit(data)
    }
}

fun <T> Flow<T>.ignoreError(): Flow<T> {
    return this.catch { ignoreErrorHandler(it) }
}

fun <T> Flow<T>.toastError(): Flow<T> {
    return this.catch { toastErrorHandler(it) }
}