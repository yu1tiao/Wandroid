package com.pretty.asp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.pretty.asp.entity.PayResp
import com.pretty.asp.entity.PayType
import com.pretty.asp.entity.WXAuthResp
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


/**
 * 在app项目中的建一个包：包名.wxapi,把这个activity拷贝进去
配置manifest：
<activity
android:name=".wxapi.WXEntryActivity"
android:label="@string/app_name"
android:theme="@android:style/Theme.Translucent.NoTitleBar"
android:exported="true"
android:taskAffinity="填写你的包名"
android:launchMode="singleTask"/>

混淆：
-keep class com.tencent.mm.opensdk.** {
 *;
}

-keep class com.tencent.wxop.** {
 *;
}

-keep class com.tencent.mm.sdk.** {
 *;
}
 */
open class WXEntryActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WXManager.api.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        WXManager.api.handleIntent(intent, this)
    }

    override fun onResp(resp: BaseResp?) {
        if (resp != null) {
            when (resp.type) {
                ConstantsAPI.COMMAND_PAY_BY_WX -> {
                    //微信支付
                    val payResp = when (resp.errCode) {
                        BaseResp.ErrCode.ERR_OK -> {
                            //支付成功
                            PayResp(PayType.WECHAT, true, "")
                        }
                        BaseResp.ErrCode.ERR_USER_CANCEL -> {
                            //用户取消
                            PayResp(PayType.WECHAT, false, "用户取消支付")
                        }
                        else -> {
                            //支付失败
                            PayResp(PayType.WECHAT, false, "支付失败")
                        }
                    }
                    // todo 发送支付结果
//                    emit(EventKey.PAY_RESP, payResp)
                }
                ConstantsAPI.COMMAND_SENDAUTH -> {
                    val wxAuthResp: WXAuthResp
                    when (resp.errCode) {
                        BaseResp.ErrCode.ERR_OK -> {
                            //用户同意
                            val authResp = resp as SendAuth.Resp
                            wxAuthResp = WXAuthResp(true, "", authResp.code)
                        }
                        BaseResp.ErrCode.ERR_USER_CANCEL -> {
                            //用户取消
                            wxAuthResp = WXAuthResp(true, "用户取消授权")

                        }
                        BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                            //用户拒绝授权
                            wxAuthResp = WXAuthResp(false, "用户拒绝授权")
                        }
                        else -> {
                            //其他原因失败
                            wxAuthResp = WXAuthResp(false, "授权失败")
                        }
                    }
                    // TODO: 2020/8/16 微信登录结果
//                    emit(EventKey.WX_AUTH_RESP, wxAuthResp)
                }
            }
        }

        finish()

    }

    override fun onReq(req: BaseReq) {
    }

}