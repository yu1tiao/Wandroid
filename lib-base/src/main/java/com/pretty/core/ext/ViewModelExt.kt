package com.pretty.core.ext

import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.Condition
import com.pretty.core.http.Resp
import com.pretty.core.http.check
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author mathew
 * @date 2020/1/8
 */

fun <T> BaseViewModel.launch(
    block: suspend () -> Resp<T>,
    success: (Resp<T>) -> Unit,
    failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
    showLoading: Boolean = false,
    condition: Condition? = null
): Job {
    return viewModelScope.launch {
        runCatching {
            if (showLoading) showLoading()
            block().check(condition)
        }.onSuccess {
            if (showLoading) hideLoading()
            success(it)
        }.onFailure {
            if (showLoading) hideLoading()
            failure(it)
        }
    }
}

