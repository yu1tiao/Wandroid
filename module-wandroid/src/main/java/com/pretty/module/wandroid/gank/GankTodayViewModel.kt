package com.pretty.module.wandroid.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.toastErrorHandler
import com.pretty.core.ext.handApiResult
import com.pretty.module.wandroid.entity.BannerBean
import com.pretty.module.wandroid.entity.GankCategoryBean
import kotlinx.coroutines.launch

class GankTodayViewModel : BaseViewModel() {

    val ldBanner = MutableLiveData<List<BannerBean>>()
    val ldCategory = MutableLiveData<List<GankCategoryBean>>()
    val ldHot = MutableLiveData<List<Any>>()
    val ldError = MutableLiveData<Any>()

    private val model by lazy { GankTodayModel() }

    fun getBanner() {
        viewModelScope.launch {
            model.getBanner()
                .onError {
                    toastErrorHandler.invoke(it)
                    ldError.value = ""
                }
                .onSuccess {
                    ldBanner.value = it
                }
        }
    }

    fun getHot() {
        viewModelScope.launch {
            try {
                val list = mutableListOf<Any>()

                val ganHuo = model.getHotData("views", "GanHuo", 10)
                handApiResult(ganHuo, {
                    it?.run {
                        list.add("热门干货")
                        list.addAll(this)
                    }
                })

                val article = model.getHotData("views", "Article", 10)
                handApiResult(article, {
                    it?.run {
                        list.add("热门文章")
                        list.addAll(this)
                    }
                })

                ldHot.value = list
            } catch (e: Exception) {
                toastErrorHandler.invoke(e)
                ldError.value = ""
            }
        }
    }

    fun getCategory() {
        viewModelScope.launch {
            model.getCategory()
                .onSuccess {
                    ldCategory.value = it
                }.onError {
                    toastErrorHandler(it)
                    ldError.value = ""
                }
        }
    }

}