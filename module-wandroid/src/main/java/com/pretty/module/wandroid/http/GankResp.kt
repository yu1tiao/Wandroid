package com.pretty.module.wandroid.http

import com.pretty.core.http.Resp

class GankResp<T>(
    var status: String,
    var data: T?
) {
    fun mapToResp(): Resp<T> {
        return Resp(data, mapStatus(), "gank error")
    }

    private fun mapStatus(): String {
        return if (status == "100") {
            "0"
        } else {
            "1"
        }
    }
}