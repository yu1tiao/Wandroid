package com.pretty.wandroid.user

import com.pretty.core.service.AccountService
import com.pretty.core.service.entity.User
import com.sankuai.waimai.router.annotation.RouterService

@RouterService(interfaces = [AccountService::class], singleton = true)
class AccountServiceImpl : AccountService {

    override fun isLogin(): Boolean {
        return true
    }

    override fun getLoginUser(): User? {
        return User("yuu")
    }

    override fun startLoginActivity() {
    }

    override fun registerUserObserver(observer: AccountService.UserObserver) {
    }

    override fun unRegisterUserObserver(observer: AccountService.UserObserver) {
    }
}