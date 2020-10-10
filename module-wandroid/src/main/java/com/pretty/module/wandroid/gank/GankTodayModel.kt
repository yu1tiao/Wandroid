package com.pretty.module.wandroid.gank

import com.pretty.core.base.BaseModel
import com.pretty.core.http.Resp
import com.pretty.core.http.RetrofitManager
import com.pretty.module.wandroid.api.GankApi
import com.pretty.module.wandroid.entity.BannerBean
import com.pretty.module.wandroid.entity.GankBean
import com.pretty.module.wandroid.entity.GankCategoryBean
import com.pretty.module.wandroid.http.GankResp
import retrofit2.http.Path

class GankTodayModel : BaseModel() {

    private val api by lazy {
        RetrofitManager.obtainService(GankApi::class.java)
    }

    suspend fun getBanner(): Resp<List<BannerBean>> {
        return api.getBanner().mapToResp()
    }

    suspend fun getCategory(): Resp<List<GankCategoryBean>> {
        return api.getCategory().mapToResp()
    }

    suspend fun getRandomData(category: String, type: String, count: Int): Resp<List<GankBean>> {
        return api.getRandomData(category, type, count).mapToResp()
    }

    suspend fun getHotData(type: String, category: String, count: Int): Resp<List<GankBean>> {
        return api.getHotData(type, category, count).mapToResp()
    }
}