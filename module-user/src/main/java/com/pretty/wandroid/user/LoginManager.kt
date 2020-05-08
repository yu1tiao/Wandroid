package com.pretty.wandroid.user

import com.pretty.core.service.AccountService
import com.pretty.core.service.entity.LoginEntity
import com.pretty.core.util.AppSPUtil

typealias UserStateObserver = AccountService.UserObserver

object LoginManager {

    private const val KEY_USER = "login_user"

    /** 当前登录用户  */
    private var loginEntity: LoginEntity? = null

    /** 存储当前监听登录状态的所有组件的name-action键值对  */
    private val USER_LOGIN_OBSERVER = mutableListOf<UserStateObserver>()
    private var loginSuccessObserver: UserStateObserver? = null

    fun addObserver(observer: UserStateObserver) {
        if (!USER_LOGIN_OBSERVER.contains(observer)) {
            USER_LOGIN_OBSERVER.add(observer)
        }
    }

    fun removeObserver(observer: UserStateObserver) {
        USER_LOGIN_OBSERVER.remove(observer)
    }

    fun setLoginSuccessObserver(observer: UserStateObserver) {
        loginSuccessObserver = observer
    }

    private fun onUserLoginStateUpdated() {
        //登录状态改变时，立即通知所有监听登录状态的组件
        if (USER_LOGIN_OBSERVER.isNotEmpty()) {
            USER_LOGIN_OBSERVER.forEach {
                it.onUserChange(loginEntity)
            }
        }
    }

    fun loginSuccess(user: LoginEntity) {
        loginSuccessObserver?.onUserChange(user)
        saveLoginEntity(user)
        loginSuccessObserver = null
    }

    fun saveLoginEntity(user: LoginEntity) {
        if (user !== loginEntity) {
            loginEntity = user
            AppSPUtil.put(KEY_USER, user)
            onUserLoginStateUpdated()
        }
    }

    fun getLoginEntity(readCache: Boolean = false): LoginEntity? {
        return when {
            loginEntity != null -> loginEntity
            readCache -> AppSPUtil.getParcelable(KEY_USER)
            else -> null
        }
    }

    fun clear() {
        loginEntity = null
        AppSPUtil.remove(KEY_USER)
    }

    fun isLogin(readCache: Boolean = false): Boolean = getLoginEntity(readCache) != null
}