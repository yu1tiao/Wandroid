package com.pretty.module.wandroid.gank

import androidx.lifecycle.MutableLiveData
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.toastErrorHandler
import com.pretty.core.ext.launch
import com.pretty.module.wandroid.entity.GankBean

class GankGirlsViewModel : BaseViewModel() {

    private var page = 1
    private val pageSize = 10
    val ldGirls = MutableLiveData<List<GankBean>>()
    val ldGirlsLoadMore = MutableLiveData<List<GankBean>>()
    val ldNoMore = MutableLiveData<Void>()
    val ldError = MutableLiveData<Void>()

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
        launch({ model.getCategoryData("Girl", "Girl", pageIndex, count) }, {
            if (isLoadMore) {
                if (it.data?.isNullOrEmpty() == true) {
                    ldNoMore.value = null
                } else {
                    ldGirlsLoadMore.value = it.data
                }
            } else {
                ldGirls.value = it.data
            }
        }, {
            toastErrorHandler(it)
            page--
            ldError.value = null
        })
    }

}