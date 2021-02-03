package com.pretty.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pretty.core.arch.ILoadable
import com.pretty.core.arch.LoadingState

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
}
