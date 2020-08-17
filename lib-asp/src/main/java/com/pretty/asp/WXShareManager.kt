package com.pretty.asp

import android.graphics.Bitmap
import com.pretty.core.Foundation
import com.pretty.core.util.L
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.util.*


object WXShareManager {
    private var APP_ID = ""

    private val api: IWXAPI by lazy {
        WXAPIFactory.createWXAPI(Foundation.getAppContext(), APP_ID, true)
    }

    fun init(appId: String) {
        require(appId.isNotEmpty()) {
            throw RuntimeException("错误的appId")
        }
        this.APP_ID = appId
        api.registerApp(APP_ID)
    }

    // 也可动态监听微信启动广播进行注册到微信
//        Foundation.getAppContext().registerReceiver(object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                // 将该app注册到微信
//                api.registerApp(APP_ID)
//            }
//        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))

    /**
     *  支持4中分享，但是一般只需要会话和朋友圈两种
    public static final int WXSceneSession = 0; // 微信会话
    public static final int WXSceneTimeline = 1;// 朋友圈
    public static final int WXSceneFavorite = 2;// 收藏
    public static final int WXSceneSpecifiedContact = 3;// 指定联系人
     */
    fun shareText(text: String, title: String, isPYQ: Boolean) {
        if (isPYQ && !checkCanShare2Pyq()) {
            // 不支持分享到朋友圈
            L.e("当前微信版本不支持分享到朋友圈")
            return
        }

        //初始化一个 WXTextObject 对象，填写分享的文本内容
        val textObj = WXTextObject(text)

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        val msg = WXMediaMessage()
        msg.mediaObject = textObj
        msg.title = title
        msg.description = text

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction()
        req.message = msg
        req.scene =
            if (isPYQ) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneSession

        //调用api接口，发送数据到微信
        api.sendReq(req)
    }

    fun shareBitmap(bitmap: Bitmap, thumbBmp: ByteArray?, title: String, isPYQ: Boolean) {
        if (isPYQ && !checkCanShare2Pyq()) {
            // 不支持分享到朋友圈
            L.e("当前微信版本不支持分享到朋友圈")
            return
        }
        val imgObj = WXImageObject(bitmap)
        val msg = WXMediaMessage(imgObj)
        msg.title = title
        //设置缩略图
        if (thumbBmp != null) {
            msg.thumbData = thumbBmp
        }

        //构造一个Req
        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction()
        req.message = msg
        req.scene =
            if (isPYQ) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneSession
//        req.userOpenId = getOpenId()

        api.sendReq(req)
    }

    fun shareUrl(url: String, title: String, desc: String, logoThumb: ByteArray?, isPYQ: Boolean) {
        val webpage = WXWebpageObject(url)

        val msg = WXMediaMessage(webpage)
        msg.title = title
        msg.description = desc
        if (logoThumb != null) {
            msg.thumbData = logoThumb
        }

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction()
        req.message = msg
        req.scene =
            if (isPYQ) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneSession
//        req.userOpenId = getOpenId()

        api.sendReq(req)
    }

    private fun buildTransaction(): String {
        return UUID.randomUUID().toString()
    }

    // 检查是否可以分享到朋友圈
    private fun checkCanShare2Pyq(): Boolean {
        return (api.wxAppSupportAPI >= Build.TIMELINE_SUPPORTED_SDK_INT)
    }
}