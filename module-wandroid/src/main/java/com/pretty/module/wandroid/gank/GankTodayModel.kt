package com.pretty.module.wandroid.gank

import com.pretty.core.base.BaseModel
import com.pretty.core.ext.safeApi
import com.pretty.core.http.ApiResult
import com.pretty.core.http.RetrofitManager
import com.pretty.module.wandroid.api.GankApi
import com.pretty.module.wandroid.entity.BannerBean
import com.pretty.module.wandroid.entity.GankBean
import com.pretty.module.wandroid.entity.GankCategoryBean

class GankTodayModel : BaseModel() {

    private val api by lazy {
        RetrofitManager.obtainService(GankApi::class.java)
    }

    suspend fun getBanner(): ApiResult<List<BannerBean>> {
        return safeApi {
            api.getBanner().mapToResp()
        }
    }

    suspend fun getCategory(): ApiResult<List<GankCategoryBean>> {
        return safeApi {
            api.getCategory().mapToResp()
        }
    }

    suspend fun getRandomData(
        category: String,
        type: String,
        count: Int
    ): ApiResult<List<GankBean>> {
        return safeApi {
            api.getRandomData(category, type, count).mapToResp()
        }
    }

    suspend fun getHotData(type: String, category: String, count: Int): ApiResult<List<GankBean>> {
        return safeApi {
            api.getHotData(type, category, count).mapToResp()
        }
    }
}