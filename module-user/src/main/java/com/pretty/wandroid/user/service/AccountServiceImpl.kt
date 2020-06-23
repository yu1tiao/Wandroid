package com.pretty.wandroid.user.service

import com.pretty.core.Foundation
import com.pretty.core.router.RC
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.AccountService
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterService

@RouterService(
    interfaces = [AccountService::class],
    key = [RC.ACCOUNT_SERVICE],
    singleton = true
)
class AccountServiceImpl : AccountService {

    override fun isLogin(): Boolean {
        return LoginManager.isLogin()
    }

    override fun logout() {
        LoginManager.clear()
    }

    override fun getLoginUser(): LoginEntity? {
        return LoginManager.getLoginEntity()
    }

    override fun registerUserObserver(observer: AccountService.UserObserver) {
        LoginManager.addObserver(observer)
    }

    override fun unRegisterUserObserver(observer: AccountService.UserObserver) {
        LoginManager.removeObserver(observer)
    }

    override fun runIfLogin(onNext: (LoginEntity) -> Unit) {
        if (isLogin()) {
            onNext(getLoginUser()!!)
        } else {
            // 添加登录监听，然后打开登录页面，此监听器会在登录页关闭时自动移除
            LoginManager.addAutoRemoveObserver(object : AccountService.UserObserver {
                override fun onUserChange(user: LoginEntity?) {
                    onNext(user!!)
                }
            })
            Router.startUri(Foundation.getTopActivity(), RC.WANDROID_LOGIN_ACTIVITY)
        }
    }
}