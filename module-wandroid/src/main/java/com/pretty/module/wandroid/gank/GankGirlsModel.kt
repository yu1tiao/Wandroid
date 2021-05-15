package com.pretty.module.wandroid.gank

import com.pretty.core.base.BaseModel
import com.pretty.core.ext.safeApi
import com.pretty.core.http.ApiResult
import com.pretty.core.http.RetrofitManager
import com.pretty.module.wandroid.api.GankApi
import com.pretty.module.wandroid.entity.GankBean

class GankGirlsModel : BaseModel() {

    private val api by lazy {
        RetrofitManager.obtainService(GankApi::class.java)
    }

    suspend fun getCategoryData(
        category: String,
        type: String,
        page: Int,
        count: Int
    ): ApiResult<List<GankBean>> {
        return safeApi {
            api.getCategoryData(category, type, page, count).mapToResp()
        }
    }
}