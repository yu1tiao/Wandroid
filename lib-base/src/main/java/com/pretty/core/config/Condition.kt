package com.pretty.core.config

import com.pretty.core.http.Resp
import okhttp3.OkHttpClient
import retrofit2.Retrofit


/** 网络请求是否成功的判断规则 */
typealias Condition = Resp<*>.() -> Boolean

/** 回调配置OkHttp */
typealias HttpClientConfigCallback = (OkHttpClient.Builder) -> OkHttpClient.Builder

/** 回调配置Retrofit */
typealias RetrofitConfigCallback = (Retrofit.Builder) -> Retrofit.Builder