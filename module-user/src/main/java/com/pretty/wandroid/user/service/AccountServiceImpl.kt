package com.pretty.wandroid.user.service

import com.blankj.utilcode.util.ActivityUtils
import com.pretty.core.arch.container.launchFragmentInContainer
import com.pretty.core.router.RouterConstant
import com.pretty.core.router.service.AccountService
import com.pretty.core.router.entity.LoginEntity
import com.pretty.wandroid.user.login.LoginFragment
import com.sankuai.waimai.router.annotation.RouterService

@RouterService(
    interfaces = [AccountService::class],
    key = [RouterConstant.ACCOUNT_SERVICE],
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

    override fun startLoginActivity() {
        launchFragmentInContainer(
            ActivityUtils.getTopActivity(),
            LoginFragment::class.java
        )
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
            LoginManager.addAutoRemoveObserver(object :
                AccountService.UserObserver {
                override fun onUserChange(user: LoginEntity?) {
                    onNext(user!!)
                }
            })
            startLoginActivity()
        }
    }
}