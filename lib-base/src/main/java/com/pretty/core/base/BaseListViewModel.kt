package com.pretty.core.base

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import com.pretty.core.util.SingleLiveEvent


/**
 * @author yu
 * @date 2019/3/5
 */
abstract class BaseListViewModel<T> : BaseViewModelRx() {

    val dataSet: MutableLiveData<List<T>> = MutableLiveData()
    val loadMoreDataSet: MutableLiveData<List<T>> = MutableLiveData()
    val loadCompleted: SingleLiveEvent<Void> = SingleLiveEvent()
    val noMore: SingleLiveEvent<Void> = SingleLiveEvent()

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
        loadCompleted.call()
    }

    @MainThread
    protected fun noMore() {
        noMore.call()
    }

}