package com.pretty.asp.entity

data class WXAuthResp(
    var success: Boolean,
    var message: String,
    var code: String = ""
)