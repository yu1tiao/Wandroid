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

class GankGirlsModel : BaseModel() {

    private val api by lazy {
        RetrofitManager.obtainService(GankApi::class.java)
    }

    suspend fun getCategoryData(
        category: String,
        type: String,
        page: Int,
        count: Int
    ): Resp<List<GankBean>> {
        return api.getCategoryData(category, type, page, count).mapToResp()
    }
}