package com.pretty.wandroid.user

import com.blankj.utilcode.util.ActivityUtils
import com.pretty.core.arch.launchFragmentInContainer
import com.pretty.core.service.AccountService
import com.pretty.core.service.entity.LoginEntity
import com.pretty.wandroid.user.login.LoginFragment
import com.sankuai.waimai.router.annotation.RouterService

@RouterService(interfaces = [AccountService::class], singleton = true)
class AccountServiceImpl : AccountService {

    override fun isLogin(): Boolean {
        return LoginManager.isLogin()
    }

    override fun getLoginUser(): LoginEntity? {
        return LoginManager.getLoginEntity()
    }

    override fun startLoginActivity() {
        launchFragmentInContainer(ActivityUtils.getTopActivity(), LoginFragment::class.java)
    }

    override fun registerUserObserver(observer: AccountService.UserObserver) {
        LoginManager.addObserver(observer)
    }

    override fun unRegisterUserObserver(observer: AccountService.UserObserver) {
        LoginManager.removeObserver(observer)
    }

    fun checkLogin(onNext: (LoginEntity) -> Unit) {
        if (isLogin()) {
            onNext(getLoginUser()!!)
        } else {
            LoginManager.setLoginSuccessObserver(object : UserStateObserver {
                override fun onUserChange(user: LoginEntity?) {
                    onNext(user!!)
                }
            })
            startLoginActivity()
        }
    }
}