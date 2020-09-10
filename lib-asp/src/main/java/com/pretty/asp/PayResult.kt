package com.pretty.asp

import java.io.Serializable

object PayType {
    const val WECHAT = 0
    const val ALI_PAY = 1
}


sealed class PayResult constructor(
    var payType: Int,
    var success: Boolean,
    var message: String? = null
) : Serializable {

    companion object {

        fun wxSuccess(): PayResult {
            return Success(PayType.WECHAT)
        }

        fun aliPaySuccess(): PayResult {
            return Success(PayType.ALI_PAY)
        }

        fun wxError(message: String): PayResult {
            return Error(PayType.WECHAT, message)
        }

        fun aliPayError(message: String): PayResult {
            return Error(PayType.ALI_PAY, message)
        }

        fun wxCancel(): PayResult {
            return Cancel(PayType.WECHAT, "微信支付取消")
        }

        fun aliPayCancel(): PayResult {
            return Cancel(PayType.ALI_PAY, "支付宝支付取消")
        }

        fun wxNotInstall(): PayResult {
            return NotInstall(PayType.WECHAT, "未安装微信")
        }

        fun aliPayNotInstall(): PayResult {
            return NotInstall(PayType.ALI_PAY, "未安装支付宝")
        }
    }

    class NotInstall(payType: Int, message: String) : PayResult(payType, false, message)
    class Success(payType: Int) : PayResult(payType, true, null)
    class Error(payType: Int, message: String) : PayResult(payType, false, message)
    class Cancel(payType: Int, message: String) : PayResult(payType, false, message)

    override fun toString(): String {
        val type = if (payType == 0) {
            "微信支付"
        } else {
            "支付宝支付"
        }
        val suc = if (success) {
            "成功"
        } else {
            "失败"
        }
        return "$type$suc message = ${message.orEmpty()}"
    }
}