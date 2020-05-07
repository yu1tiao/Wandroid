package com.pretty.core.service

import com.pretty.core.service.entity.User

interface AccountService {

    interface UserObserver {
        fun onUserChange(user: User?)
    }

    fun isLogin(): Boolean

    fun getLoginUser(): User?

    fun startLoginActivity()

    fun registerUserObserver(observer: UserObserver)

    fun unRegisterUserObserver(observer: UserObserver)
}