package com.pretty.core.ext

import androidx.lifecycle.MutableLiveData
import com.pretty.core.Foundation
import com.pretty.core.http.ApiResult
import com.pretty.core.http.Resp
import com.pretty.core.http.check
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author mathew
 * @date 2020/1/8
 */

suspend fun <T> safeApi(req: suspend () -> Resp<T>): ApiResult<T> {
    return try {
        val resp = req().check()
        ApiResult.success(resp.data)
    } catch (e: Exception) {
        ApiResult.error(e)
    }
}

fun <T> Resp<T>.toFlow(): Flow<T> {
    return flow {
        val data = this@toFlow.check().data
            ?: throw NullPointerException("flow 不允许发射空值，请用safeApi处理可空的请求")
        emit(data)
    }
}

//
//inline fun <reified T> CoroutineScope.safeLaunch(
//    noinline block: suspend () -> ApiResult<T>,
//    noinline success: (T?) -> Unit,
//    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
//    noinline start: (() -> Unit)? = null,
//    noinline end: (() -> Unit)? = null
//): Job {
//    return launch {
//        start?.invoke()
//        runCatching {
//            block()
//        }.onSuccess {
//            handApiResult(it, success, failure)
//        }.onFailure {
//            failure(it)
//        }
//        end?.invoke()
//    }
//}
//
//inline fun <reified T> BaseViewModel.safeLaunch(
//    noinline block: suspend () -> ApiResult<T>,
//    noinline success: (T?) -> Unit,
//    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
//    showLoading: Boolean = true
//): Job {
//    return viewModelScope.safeLaunch(
//        block, success, failure,
//        start = {
//            if (showLoading) showLoading()
//        },
//        end = {
//            if (showLoading) hideLoading()
//        })
//}
//
//inline fun <reified T> CoroutineScope.safeFlow(
//    noinline block: suspend () -> Flow<T>,
//    noinline success: (T) -> Unit,
//    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
//    noinline start: (() -> Unit)? = null,
//    noinline end: (() -> Unit)? = null
//): Job {
//    return launch {
//        start?.invoke()
//        block().catch {
//            failure(it)
//        }.collectLatest {
//            success(it)
//        }
//        end?.invoke()
//    }
//}
//
//inline fun <reified T> BaseViewModel.safeFlow(
//    noinline block: suspend () -> Flow<T>,
//    noinline success: (T) -> Unit,
//    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
//    showLoading: Boolean = true
//): Job {
//    return viewModelScope.safeFlow(
//        block, success, failure,
//        start = {
//            if (showLoading) showLoading()
//        },
//        end = {
//            if (showLoading) hideLoading()
//        })
//}

inline fun <reified T> handApiResult(
    result: ApiResult<T>,
    liveData: MutableLiveData<T>,
    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler
) {
    handApiResult(result, success = {
        liveData.postValue(it)
    }, failure = failure)
}

inline fun <reified T> handApiResult(
    result: ApiResult<T>,
    noinline success: (T?) -> Unit,
    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler
) {
    if (result.isSuccess) {
        success(result.getOrNull())
    } else {
        failure(result.exceptionOrNull()!!)
    }
}

