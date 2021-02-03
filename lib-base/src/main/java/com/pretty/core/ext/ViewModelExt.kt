package com.pretty.core.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.http.Resp
import com.pretty.core.http.check
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author mathew
 * @date 2020/1/8
 */

fun <T> BaseViewModel.launch(
    block: suspend () -> Resp<T>,
    success: (Resp<T>) -> Unit,
    failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
    showLoading: Boolean = false
): Job {
    return viewModelScope.launch {
        runCatching {
            if (showLoading) showLoading()
            block().check()
        }.onSuccess {
            if (showLoading) hideLoading()
            success(it)
        }.onFailure {
            if (showLoading) hideLoading()
            failure(it)
        }
    }
}

/**
 * 后台执行任务
 */
fun <T> ViewModel.async(
    block: () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}