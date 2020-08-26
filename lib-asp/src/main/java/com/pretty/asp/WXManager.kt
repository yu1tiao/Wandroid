package com.pretty.asp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.pretty.core.Foundation
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import okhttp3.*
import java.io.IOException

object WXManager {
    private var APP_ID = ""

    val api: IWXAPI by lazy {
        WXAPIFactory.createWXAPI(Foundation.getAppContext(), APP_ID, true)
    }

    fun init() {
        require(APP_ID.isNotEmpty()) {
            throw RuntimeException("请先设置appId")
        }
        api.registerApp(APP_ID)
    }

    fun setAppId(appId: String) {
        require(appId.isNotEmpty()) {
            throw RuntimeException("错误的appId")
        }
        APP_ID = appId
    }

    fun isInstalled(): Boolean = api.isWXAppInstalled

    fun openApp() = api.openWXApp()

    /**
     * 唤起微信登录, 回调中返回code，用于获取access_token
     */
    fun sendAuthReq() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = Foundation.getAppContext().packageName + "auth_"
        api.sendReq(req)
    }

    fun requestAccessToken(code: Int, secret: String) {
        val url =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=$APP_ID&secret=${secret}&code=${code}&grant_type=authorization_code"
        val request = Request.Builder().url(url).get().build()
        OkHttpClient.Builder().build().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                /*
                {
                  "access_token": "ACCESS_TOKEN",
                  "expires_in": 7200,
                  "refresh_token": "REFRESH_TOKEN",
                  "openid": "OPENID",
                  "scope": "SCOPE"
                }
                * */
            }
        })
    }

    fun requestUserInfo(accessToken: String, openId: String) {
        val url = "https://api.weixin.qq.com/sns/userinfo?access_token=$accessToken&openid=$openId"
        val request = Request.Builder().url(url).get().build()
        OkHttpClient.Builder().build().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                /*
                {
                  "openid": "OPENID",
                  "nickname": "NICKNAME",
                  "sex": 1,
                  "province": "PROVINCE",
                  "city": "CITY",
                  "country": "COUNTRY",
                  "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
                  "privilege": ["PRIVILEGE1", "PRIVILEGE2"],
                  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
                }
                * */
            }
        })
    }

    /**
     * 唤起微信支付
     */
    fun sendPayReq(orderPayInfo: WXOrderPayInfo, callback: (Int) -> Unit) {
        val payReq = PayReq()
        payReq.appId = orderPayInfo.appid
        payReq.partnerId = orderPayInfo.partnerId
        payReq.prepayId = orderPayInfo.prepayId
        //Sign=WXPay
        payReq.packageValue = orderPayInfo.packageValue
        payReq.nonceStr = orderPayInfo.nonceStr
        payReq.timeStamp = orderPayInfo.timeStamp
        payReq.sign = orderPayInfo.sign

        api.sendReq(payReq)

        // 接收回调，注意支付成功只是一个通知，具体是否成功要以服务端为准
        PayResultReceiver.register(callback)
    }

    /**
     * 打开小程序
     */
    fun openMiniProgram(miniProgramId: String, isRelease: Boolean, path: String? = null) {
        val req = WXLaunchMiniProgram.Req()
        req.userName = miniProgramId
        if (!path.isNullOrBlank()) {
            req.path = path // 拉起小程序带入的参数如 "?foo=bar"
        }
        // 可选打开 开发版，体验版和正式版
        req.miniprogramType = if (isRelease) {
            WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
        } else {
            WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST
        }
        api.sendReq(req)
    }

}

data class WXOrderPayInfo(
    var appid: String,
    var partnerId: String,// 微信支付分配的商户号
    var prepayId: String,// 支付交易会话ID
    var packageValue: String = "Sign=WXPay",
    var nonceStr: String,// 随机字符串
    var timeStamp: String,// 时间戳/秒
    var sign: String// 签名信息
)

class PayResultReceiver(private val callback: (Int) -> Unit) : BroadcastReceiver() {
    companion object {
        private const val ACTION_PAY_RESULT = "ACTION_PAY_RESULT"

        fun register(callback: (Int) -> Unit) {
            LocalBroadcastManager.getInstance(Foundation.getAppContext())
                .registerReceiver(PayResultReceiver(callback), IntentFilter(ACTION_PAY_RESULT))
        }

        @JvmStatic
        fun sendResult(code: Int) {
            val intent = Intent().apply {
                action = ACTION_PAY_RESULT
                putExtra("PayResultCode", code)
            }
            LocalBroadcastManager.getInstance(Foundation.getAppContext()).sendBroadcast(intent)
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        LocalBroadcastManager.getInstance(Foundation.getAppContext()).unregisterReceiver(this)
        if (intent.action == ACTION_PAY_RESULT) {
            val result = intent.getIntExtra("PayResultCode", -999)
            callback.invoke(result)
        }
    }
}