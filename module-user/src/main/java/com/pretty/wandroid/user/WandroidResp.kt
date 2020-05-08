package com.pretty.wandroid.user

import com.pretty.core.http.Resp

/**
 * wandroid 的数据结构
 */
class WandroidResp<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String?
) : Resp<T> {

    override fun getRespData(): T? {
        return data
    }

    override fun getCode(): String {
        return errorMsg.toString()
    }

    override fun getMessage(): String? {
        return errorMsg
    }

    override fun isSuccessful(): Boolean {
        return errorCode == 0
    }
}