package com.pretty.module.wandroid.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.handApiResult
import com.pretty.core.ext.safeLaunch
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
        safeLaunch({ model.getBanner() }, {
            ldBanner.value = it
        }, {
            Foundation.getGlobalConfig().errorHandler.invoke(it)
            ldError.value = ""
        })
    }

    fun getHot() {
        viewModelScope.launch {
            try {
                val article = model.getHotData("views", "Article", 10)
                val ganHuo = model.getHotData("views", "GanHuo", 10)

                val list = mutableListOf<Any>()

                handApiResult(ganHuo, {
                    it?.run {
                        list.add("热门干货")
                        list.addAll(this)
                    }
                })
                handApiResult(article, {
                    it?.run {
                        list.add("热门文章")
                        list.addAll(this)
                    }
                })

                ldHot.value = list
            } catch (e: Exception) {
                Foundation.getGlobalConfig().errorHandler.invoke(e)
                ldError.value = ""
            }
        }
    }

    fun getCategory() {
        safeLaunch({
            model.getCategory()
        }, {
            ldCategory.value = it
        }, {
            Foundation.getGlobalConfig().errorHandler.invoke(it)
            ldError.value = ""
        })
    }

}