package com.pretty.module.wandroid.api

import com.pretty.module.wandroid.entity.BannerBean
import com.pretty.module.wandroid.entity.GankBean
import com.pretty.module.wandroid.entity.GankCategoryBean
import com.pretty.module.wandroid.http.GankResp
import retrofit2.http.GET
import retrofit2.http.Path

interface GankApi {

    @GET("https://gank.io/api/v2/banners")
    suspend fun getBanner(): GankResp<List<BannerBean>>

    @GET("https://gank.io/api/v2/categories/Article")
    suspend fun getCategory(): GankResp<List<GankCategoryBean>>

    @GET("https://gank.io/api/v2/random/category/{category}/type/{type}/count/{count}")
    suspend fun getRandomData(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("count") count: Int
    ): GankResp<List<GankBean>>

    @GET("https://gank.io/api/v2/hot/{type}/category/{category}/count/{count}")
    suspend fun getHotData(
        @Path("type") type: String,
        @Path("category") category: String,
        @Path("count") count: Int
    ): GankResp<List<GankBean>>
}