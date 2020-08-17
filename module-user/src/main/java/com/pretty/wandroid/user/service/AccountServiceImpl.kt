package com.pretty.wandroid.user.service

import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.pretty.core.Foundation
import com.pretty.core.router.RC
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.AccountService
import com.pretty.core.router.service.LoginReceiver
import com.pretty.core.util.L
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterService

@RouterService(interfaces = [AccountService::class], key = [RC.ACCOUNT_SERVICE], singleton = true)
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
            LocalBroadcastManager.getInstance(Foundation.getAppContext())
                .registerReceiver(
                    LoginReceiver(createLoginCallback(onNext)),
                    createLoginIntentFilter()
                )
            Router.startUri(Foundation.getTopActivity(), RC.WANDROID_LOGIN_ACTIVITY)
        }
    }

    private fun createLoginIntentFilter(): IntentFilter {
        return IntentFilter().apply {
            addAction(LoginReceiver.ACTION_LOGIN_SUC)
            addAction(LoginReceiver.ACTION_LOGIN_CANCEL)
        }
    }

    private fun createLoginCallback(onNext: (LoginEntity) -> Unit): AccountService.Callback {
        return object : AccountService.Callback {
            override fun onSuccess(user: LoginEntity) {
                onNext.invoke(user)
            }

            override fun onCancel() {
                // do nothing
                L.i("用户取消登录")
            }
        }
    }
}