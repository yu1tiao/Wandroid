package com.pretty.asp

import com.blankj.utilcode.util.EncryptUtils
import com.pretty.core.Foundation
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import okhttp3.*
import java.io.IOException
import kotlin.random.Random

object WXManager {
    private var APP_ID = ""

    val api: IWXAPI by lazy {
        WXAPIFactory.createWXAPI(Foundation.getAppContext(), APP_ID, true)
    }

    fun init(appId: String) {
        require(appId.isNotEmpty()) {
            throw RuntimeException("错误的appId")
        }
        this.APP_ID = appId
        api.registerApp(APP_ID)
    }

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
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=${APP_ID}&secret=${secret}&code=${code}&grant_type=authorization_code"
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
//    fun sendPayReq(orderPayInfo: OrderPayInfo) {
//        val payReq = PayReq()
//        payReq.appId = orderPayInfo.appid
//        payReq.partnerId = orderPayInfo.partnerid
//        payReq.prepayId = orderPayInfo.prepayid
//        //Sign=WXPay
//        payReq.packageValue = orderPayInfo.package
//        payReq.nonceStr = orderPayInfo.noncestr
//        payReq.timeStamp = orderPayInfo.timestamp
//        payReq.sign = orderPayInfo.sign
//        api.sendReq(payReq)
//    }

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

    /**
     * 生成随机字符串
     */
    private fun genNonceStr(): String {
        val random = Random(System.currentTimeMillis())
        return EncryptUtils.encryptMD5ToString(APP_ID + random.nextInt(10000) + System.currentTimeMillis())
    }

    /**
     * 获取时间戳字符串
     */
    private fun getTimestampStr(): String {
        return (System.currentTimeMillis() * 1000).toString()
    }

}