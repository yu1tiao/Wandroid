package com.pretty.core.http

import com.pretty.core.exception.ApiException

/**
 * 网络请求返回的数据接口
 * @author mathew
 * @date 2020/3/12
 */
interface Resp<T> : FlatResp {
    fun getData(): T?
}

/**
 * 扁平的接口返回，数据和状态放在同一层，也封装一个接口
 * 例如这种：
 * {code:200,message:"ok",data1:1,data2:2}
 */
interface FlatResp {
    fun getCode(): String
    fun getMessage(): String?
    fun isSuccessful(): Boolean
}


fun <T : FlatResp> checkHttp(t: T): T {
    return if (t.isSuccessful()) {
        t
    } else {
        throw ApiException(t.getCode(), t.getMessage())
    }
}
