package com.pretty.module.wandroid.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.toastErrorHandler
import com.pretty.core.http.check
import com.pretty.core.util.SingleLiveEvent
import com.pretty.module.wandroid.entity.BannerBean
import com.pretty.module.wandroid.entity.GankBean
import com.pretty.module.wandroid.entity.GankCategoryBean
import kotlinx.coroutines.launch
import java.lang.Exception

class GankGirlsViewModel : BaseViewModel() {

    private var page = 1
    private val pageSize = 10
    val ldGirls = MutableLiveData<List<GankBean>>()
    val ldGirlsLoadMore = MutableLiveData<List<GankBean>>()
    val ldNoMore = SingleLiveEvent<Void>()
    val ldError = SingleLiveEvent<Void>()

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
                    ldNoMore.call()
                } else {
                    ldGirlsLoadMore.value = it.data
                }
            } else {
                ldGirls.value = it.data
            }
        }, {
            toastErrorHandler(it)
            page--
            ldError.call()
        })
    }

}