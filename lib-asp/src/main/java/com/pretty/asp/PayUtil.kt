package com.pretty.asp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.alipay.sdk.app.PayTask
import com.tencent.mm.opensdk.modelbase.BaseResp

object PayUtil {

//    /**
//     * 微信支付
//     */
//    fun payByWX(appid: String, orderPayInfo: OrderPayInfo): Observable<PayResult> {
//        return Observable.create<PayResult> {
//            WXManager.setAppId(appid)
//            WXManager.init()
//            if (!WXManager.isInstalled()) {
//                it.onNext(PayResult.wxNotInstall())
//                it.onComplete()
//            } else {
//                WXManager.sendPayReq(orderPayInfo) { errorCode ->
//                    val result = when (errorCode) {
//                        BaseResp.ErrCode.ERR_OK -> PayResult.wxSuccess()
//                        BaseResp.ErrCode.ERR_USER_CANCEL -> PayResult.wxCancel()
//                        else -> PayResult.wxError("支付失败") // 其他支付失败
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
//    fun payByAliPay(activity: Activity, orderInfo: String): Observable<PayResult> {
//        return Observable.create<PayResult> {
//            if (!isAliPayInstalled(activity)) {
//                val result = PayResult.aliPayNotInstall()
//                it.onNext(result)
//                it.onComplete()
//                return@create
//            }
//
//            val alipay = PayTask(activity)
//            val resultMap = alipay.payV2(orderInfo, true)
//
//            // 支付宝返回的是一个map，参考  https://opendocs.alipay.com/open/204/105301
//            val r = when (resultMap["resultStatus"]) {
//                "9000" -> PayResult.aliPaySuccess()
////                "8000" -> PayResult(PayType.ALI_PAY, false, "正在支付中")
//                "6001" -> PayResult.aliPayCancel()
//                else -> PayResult.aliPayError("支付失败")
//            }
//
//            it.onNext(r)
//            it.onComplete()
//        }.ioToMain()
//    }

    private fun isAliPayInstalled(context: Context): Boolean {
        val uri = "alipays://platformapi/startApp".toUri()
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val componentName = intent.resolveActivity(context.packageManager)
        return componentName != null
    }
}