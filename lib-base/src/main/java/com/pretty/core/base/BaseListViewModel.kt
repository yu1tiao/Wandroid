package com.pretty.core.base

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData


/**
 * @author yu
 * @date 2019/3/5
 */
abstract class BaseListViewModel<T> : BaseViewModel() {

    val dataSet: MutableLiveData<List<T>> = MutableLiveData()
    val loadMoreDataSet: MutableLiveData<List<T>> = MutableLiveData()
    val loadCompleted: MutableLiveData<Void> = MutableLiveData()
    val loadMoreError: MutableLiveData<Void> = MutableLiveData()
    val noMore: MutableLiveData<Void> = MutableLiveData()

    abstract fun refresh()

    abstract fun loadMore()

    @MainThread
    protected fun postDataSet(list: List<T>) {
        dataSet.value = list
    }

    @MainThread
    protected fun postLoadMoreDataSet(list: List<T>) {
        loadMoreDataSet.value = list
    }

    @MainThread
    protected fun loadCompleted() {
        loadCompleted.value = null
    }

    @MainThread
    protected fun loadMoreError() {
        loadMoreError.value = null
    }

    @MainThread
    protected fun noMore() {
        noMore.value = null
    }

}