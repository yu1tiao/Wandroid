package com.pretty.module.wandroid.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.toastErrorHandler
import com.pretty.module.wandroid.entity.GankBean
import kotlinx.coroutines.launch

class GankGirlsViewModel : BaseViewModel() {

    private var page = 1
    private val pageSize = 10
    val ldGirls = MutableLiveData<List<GankBean>>()
    val ldGirlsLoadMore = MutableLiveData<List<GankBean>>()
    val ldNoMore = MutableLiveData<Any>()
    val ldError = MutableLiveData<Any>()

    private val model by lazy { GankGirlsModel() }

    fun refresh() {
        page = 1
        getGirl(page, pageSize)
    }

    fun loadMore() {
        page++
        getGirl(page, pageSize)
    }

    private fun getGirl(pageIndex: Int, count: Int) {
        val isLoadMore = pageIndex > 1
        viewModelScope.launch {
            model.getCategoryData("Girl", "Girl", pageIndex, count)
                .onSuccess {
                    if (isLoadMore) {
                        if (it?.isNullOrEmpty() == true) {
                            ldNoMore.value = ""
                        } else {
                            ldGirlsLoadMore.value = it
                        }
                    } else {
                        ldGirls.value = it
                    }
                }.onError {
                    toastErrorHandler(it)
                    page--
                    ldError.value = ""
                }
        }
    }

}