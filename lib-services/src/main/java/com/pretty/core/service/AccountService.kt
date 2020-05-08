package com.pretty.core.service

import com.pretty.core.service.entity.LoginEntity

interface AccountService {

    interface UserObserver {
        fun onUserChange(user: LoginEntity?)
    }

    fun isLogin(): Boolean

    fun getLoginUser(): LoginEntity?

    fun startLoginActivity()

    fun registerUserObserver(observer: UserObserver)

    fun unRegisterUserObserver(observer: UserObserver)
}