package com.pretty.wandroid.user.service

import com.pretty.core.router.service.AccountService
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.util.AppSPUtil

typealias UserStateObserver = AccountService.UserObserver

object LoginManager {

    private const val KEY_USER = "login_user"

    /** 当前登录用户  */
    private var loginEntity: LoginEntity? = null

    /** 监听user变化的observer，当保存LoginEntity时会遍历通知  */
    private val observers = mutableSetOf<UserStateObserver>()

    /** 专用于登录的时候监听user，在登录页面退出时自动remove */
    private val removeWhenLoginPageFinishObservers = mutableSetOf<UserStateObserver>()

    fun addObserver(observer: UserStateObserver) {
        if (!observers.contains(observer)) {
            observers.add(observer)
        }
    }

    fun removeObserver(observer: UserStateObserver) {
        observers.remove(observer)
    }

    fun addAutoRemoveObserver(observer: UserStateObserver) {
        removeWhenLoginPageFinishObservers.add(observer)
    }

    private fun onUserLoginStateUpdated() {
        //登录状态改变时，立即通知所有监听登录状态的组件
        if (observers.isNotEmpty()) {
            observers.forEach {
                it.onUserChange(loginEntity)
            }
        }
        if (removeWhenLoginPageFinishObservers.isNotEmpty()) {
            removeWhenLoginPageFinishObservers.forEach {
                it.onUserChange(loginEntity)
            }
        }
    }

    fun saveLoginEntity(user: LoginEntity) {
        if (user !== loginEntity) {
            loginEntity = user
            AppSPUtil.put(KEY_USER, user)
            onUserLoginStateUpdated()
        }
    }

    fun getLoginEntity(readCache: Boolean = true): LoginEntity? {
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

    fun isLogin(readCache: Boolean = false): Boolean = getLoginEntity(
        readCache
    ) != null

    fun onLoginPageFinish() {
        removeWhenLoginPageFinishObservers.clear()
    }
}