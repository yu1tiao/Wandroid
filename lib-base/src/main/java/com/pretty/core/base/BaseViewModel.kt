package com.pretty.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.arch.ILoadable
import com.pretty.core.arch.LoadingState
import com.pretty.core.http.Resp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author yu
 * @date 2018/10/29
 */
open class BaseViewModel : ViewModel(), ILoadable {

    val loading = MutableLiveData<LoadingState>()

    override fun showLoading(message: String?) {
        loading.value = LoadingState.loading(message)
    }

    override fun hideLoading() {
        loading.value = LoadingState.hide()
    }

    protected fun <T> launch(
        block: suspend () -> Resp<T>,
        success: (Resp<T>) -> Unit,
        failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
        finally: () -> Unit = {},
        showLoading: Boolean = false
    ): Job {
        return viewModelScope.launch {
            try {
                if (showLoading) showLoading()
                val resp = block()
                success(resp)
            } catch (e: Throwable) {
                failure(e)
            } finally {
                if (showLoading) hideLoading()
                finally()
            }
        }
    }

}