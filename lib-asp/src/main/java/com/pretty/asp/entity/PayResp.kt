package com.pretty.asp.entity

object PayType {
    const val WECHAT = 0
    const val ALI_PAY = 1
}

data class PayResp(
    var payType: Int,
    var success: Boolean,
    var message: String? = null
)