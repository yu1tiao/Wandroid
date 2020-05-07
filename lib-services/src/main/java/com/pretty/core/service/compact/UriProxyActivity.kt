package com.pretty.core.service.compact

import android.app.Activity
import android.os.Bundle
import com.sankuai.waimai.router.common.DefaultUriRequest
import com.sankuai.waimai.router.core.OnCompleteListener
import com.sankuai.waimai.router.core.UriRequest

/**
 * 外部路由跳转代理
 * 需要在manifest中配置scheme
 */
class UriProxyActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DefaultUriRequest.startFromProxyActivity(this, object : OnCompleteListener {
            override fun onSuccess(request: UriRequest) {
                finish()
            }

            override fun onError(request: UriRequest, resultCode: Int) {
                finish()
            }
        })
    }
}