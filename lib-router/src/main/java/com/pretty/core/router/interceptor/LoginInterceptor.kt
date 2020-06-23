package com.pretty.core.router.interceptor

import com.pretty.core.router.RC
import com.pretty.core.router.service.AccountService
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.core.UriCallback
import com.sankuai.waimai.router.core.UriInterceptor
import com.sankuai.waimai.router.core.UriRequest

/**
 * 登录拦截器
 */
class LoginInterceptor : UriInterceptor {

    private val service by lazy {
        Router.getService(AccountService::class.java, RC.ACCOUNT_SERVICE)
    }

    override fun intercept(request: UriRequest, callback: UriCallback) {
        service.runIfLogin {
            callback.onNext()
        }
    }
}