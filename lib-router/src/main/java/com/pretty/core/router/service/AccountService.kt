package com.pretty.core.router.service

import com.pretty.core.router.entity.LoginEntity

interface AccountService {

    interface UserObserver {
        fun onUserChange(user: LoginEntity?)
    }

    fun logout()

    fun isLogin(): Boolean

    fun getLoginUser(): LoginEntity?

    fun registerUserObserver(observer: UserObserver)

    fun unRegisterUserObserver(observer: UserObserver)

    fun runIfLogin(onNext: (LoginEntity) -> Unit)
}