package com.pretty.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.arch.ILoadable
import com.pretty.core.arch.LoadingState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * @author yu
 * @date 2018/10/29
 */
open class BaseViewModel : ViewModel(), ILoadable {

    val tips = MutableLiveData<String>()
    val loading = MutableLiveData<LoadingState>()

    protected fun showTips(message: String) = tips.postValue(message)

    override fun showLoading(message: String?) {
        loading.value = LoadingState.loading(message)
    }

    override fun hideLoading() {
        loading.value = LoadingState.hide()
    }

    /**
     * 创建协程并执行，运行在viewModelScope，可感知生命周期，自动取消协程
     */
    protected fun <T> launch(
        block: suspend () -> T,
        success: (T) -> Unit,
        failure: (Throwable) -> Unit = Foundation.getGlobalConfig().errorHandler,
        finally: () -> Unit = {},
        showLoading: Boolean = false
    ): Job {
        return viewModelScope.launch {
            if (showLoading) showLoading()
            try {
                success(block())
            } catch (e: Throwable) {
                failure(e)
            } finally {
                finally()
                if (showLoading) hideLoading()
            }
        }
    }

}