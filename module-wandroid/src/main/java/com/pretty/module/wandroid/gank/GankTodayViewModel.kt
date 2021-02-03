package com.pretty.module.wandroid.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.Foundation
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.launch
import com.pretty.core.http.check
import com.pretty.module.wandroid.entity.BannerBean
import com.pretty.module.wandroid.entity.GankCategoryBean
import kotlinx.coroutines.launch
import java.lang.Exception

class GankTodayViewModel : BaseViewModel() {

    val ldBanner = MutableLiveData<List<BannerBean>>()
    val ldCategory = MutableLiveData<List<GankCategoryBean>>()
    val ldHot = MutableLiveData<List<Any>>()
    val ldError = MutableLiveData<Void>()

    private val model by lazy { GankTodayModel() }

    fun getBanner() {
        launch({ model.getBanner() }, {
            ldBanner.value = it.data
        }, {
            Foundation.getGlobalConfig().errorHandler.invoke(it)
            ldError.value = null
        })
    }

    fun getHot() {
        viewModelScope.launch {
            try {
                val article = model.getHotData("views", "Article", 10).check()
                val ganHuo = model.getHotData("views", "GanHuo", 10).check()

                val list = mutableListOf<Any>()
                ganHuo.data?.let {
                    list.add("热门干货")
                    list.addAll(it)
                }
                article.data?.let {
                    list.add("热门文章")
                    list.addAll(it)
                }
                ldHot.value = list
            } catch (e: Exception) {
                Foundation.getGlobalConfig().errorHandler.invoke(e)
                ldError.value = null
            }
        }
    }

    fun getCategory() {
        launch({
            val c = model.getCategory().check()

            // 分类的封面图太丑了，这里请求对应数量的妹子图然后替换封面图
            if (c.data?.size ?: 0 > 0) {
                val girls = model.getRandomData("Girl", "Girl", c.data!!.size)
                    .check()
                    .data.orEmpty()

                girls.forEachIndexed { index, bean ->
                    c.data?.get(index)?.coverImageUrl = bean.url
                }
            }

            c
        }, {
            ldCategory.value = it.data
        }, {
            Foundation.getGlobalConfig().errorHandler.invoke(it)
            ldError.value = null
        })
    }

}