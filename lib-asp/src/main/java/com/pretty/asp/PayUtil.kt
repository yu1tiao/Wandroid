package com.pretty.asp

import android.app.Activity
import com.alipay.sdk.app.PayTask
import com.pretty.asp.entity.PayResp
import com.pretty.asp.entity.PayType
import com.tencent.mm.opensdk.modelbase.BaseResp
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object PayUtil {

    suspend fun payByWX(appid: String, orderPayInfo: WXOrderPayInfo): PayResp {
        return suspendCoroutine { con ->
            WXManager.setAppId(appid)
            WXManager.init()
            if (!WXManager.isInstalled()) {
                con.resume(PayResp(PayType.WECHAT, false, "未安装微信"))
            } else {
                WXManager.sendPayReq(orderPayInfo) { errorCode ->
                    val result = when (errorCode) {
                        BaseResp.ErrCode.ERR_OK -> PayResp(PayType.WECHAT, true, "支付成功")
                        BaseResp.ErrCode.ERR_USER_CANCEL -> PayResp(PayType.WECHAT, false, "支付取消")
                        else -> PayResp(PayType.WECHAT, false, "支付失败") // 其他支付失败
                    }
                    con.resume(result)
                }
            }
        }
    }

    suspend fun payByAliPay(activity: Activity, orderInfo: String): PayResp {
        return suspendCoroutine { con ->
            val alipay = PayTask(activity)
            val resultMap = alipay.payV2(orderInfo, true)

            // 支付宝返回的是一个map，参考  https://opendocs.alipay.com/open/204/105301
            val r = when (resultMap["resultStatus"]) {
                "9000" -> PayResp(PayType.ALI_PAY, true, "支付成功")
//                "8000" -> PayResultEntity(PayType.ALI_PAY, false, "正在支付中")
                "6001" -> PayResp(PayType.ALI_PAY, false, "支付取消")
                else -> PayResp(PayType.ALI_PAY, false, "支付失败")
            }

            con.resume(r)
        }
    }

//    /**
//     * 微信支付
//     */
//    fun payByWX(appid: String, orderPayInfo: WXOrderPayInfo): Observable<PayResp> {
//        return Observable.create<PayResp> {
//            WXManager.setAppId(appid)
//            WXManager.init()
//            if (!WXManager.isInstalled()) {
//                it.onNext(PayResp(PayType.WECHAT, false, "未安装微信"))
//                it.onComplete()
//            } else {
//                WXManager.sendPayReq(orderPayInfo) { errorCode ->
//                    val result = when (errorCode) {
//                        BaseResp.ErrCode.ERR_OK -> PayResp(PayType.WECHAT, true, "支付成功")
//                        BaseResp.ErrCode.ERR_USER_CANCEL -> PayResp(PayType.WECHAT, false, "支付取消")
//                        else -> PayResp(PayType.WECHAT, false, "支付失败") // 其他支付失败
//                    }
//                    it.onNext(result)
//                    it.onComplete()
//                }
//            }
//        }.ioToMain()
//    }
//
//    /**
//     * 支付宝支付
//     * orderInfo由服务端生成
//     */
//    fun payByAliPay(activity: Activity, orderInfo: String): Observable<PayResp> {
//        return Observable.create<PayResp> {
//            val alipay = PayTask(activity)
//            val resultMap = alipay.payV2(orderInfo, true)
//
//            // 支付宝返回的是一个map，参考  https://opendocs.alipay.com/open/204/105301
//            val r = when (resultMap["resultStatus"]) {
//                "9000" -> PayResp(PayType.ALI_PAY, true, "支付成功")
////                "8000" -> PayResultEntity(PayType.ALI_PAY, false, "正在支付中")
//                "6001" -> PayResp(PayType.ALI_PAY, false, "支付取消")
//                else -> PayResp(PayType.ALI_PAY, false, "支付失败")
//            }
//
//            it.onNext(r)
//            it.onComplete()
//        }.ioToMain()
//    }
}