package com.pretty.core.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.http.ApiResult
import com.pretty.core.http.Resp
import com.pretty.core.http.check
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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

inline fun <reified T> CoroutineScope.safeLaunch(
    noinline block: suspend () -> ApiResult<T>,
    noinline success: (T?) -> Unit,
    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
): Job {
    return launch {
        runCatching {
            block()
        }.onSuccess {
            handApiResult(it, success, failure)
        }.onFailure {
            failure(it)
        }
    }
}

inline fun <reified T> CoroutineScope.safeFlow(
    noinline block: suspend () -> ApiResult<T>,
    noinline success: (T?) -> Unit,
    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
): Job {
    return launch {
        flow<ApiResult<T>> {
            block()
        }.catch {
            failure(it)
        }.collectLatest {
            handApiResult(it, success, failure)
        }
    }
}

inline fun <reified T> BaseViewModel.safeLaunch(
    noinline block: suspend () -> ApiResult<T>,
    noinline success: (T?) -> Unit,
    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
    showLoading: Boolean = true
): Job {
    return viewModelScope.launch {
        if (showLoading) showLoading()
        runCatching {
            block()
        }.onSuccess {
            handApiResult(it, success, failure)
        }.onFailure {
            failure(it)
        }
        if (showLoading) hideLoading()
    }
}

inline fun <reified T> BaseViewModel.safeFlow(
    noinline block: suspend () -> ApiResult<T>,
    noinline success: (T?) -> Unit,
    noinline failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
    showLoading: Boolean = true
): Job {
    return viewModelScope.launch {
        if (showLoading) showLoading()
        flow<ApiResult<T>> {
            block()
        }.catch {
            failure(it)
        }.collectLatest {
            handApiResult(it, success, failure)
        }
        if (showLoading) hideLoading()
    }
}

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

