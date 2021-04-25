package com.pretty.core.router.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.pretty.core.Foundation
import com.pretty.core.router.entity.LoginEntity

interface AccountService {

    interface Callback {
        fun onSuccess(user: LoginEntity)
        fun onCancel()
    }

    fun isLogin(): Boolean

    fun getLoginUser(): LoginEntity?

    fun runIfLogin(onNext: (LoginEntity) -> Unit)
}

class LoginReceiver(private var callback: AccountService.Callback) : BroadcastReceiver() {
    companion object {
        const val ACTION_LOGIN_SUC = "com.receiver.login.ACTION_LOGIN_SUC"
        const val ACTION_LOGIN_CANCEL = "com.receiver.login.ACTION_LOGIN_CANCEL"

        @JvmStatic
        fun sendLoginSuccess(user: LoginEntity) {
            Intent().let {
                it.action = ACTION_LOGIN_SUC
                it.putExtra("LoginEntity", user)
                LocalBroadcastManager.getInstance(Foundation.getAppContext()).sendBroadcast(it)
            }
        }

        @JvmStatic
        fun sendLoginCancel() {
            Intent().let {
                it.action = ACTION_LOGIN_CANCEL
                LocalBroadcastManager.getInstance(Foundation.getAppContext()).sendBroadcast(it)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        // 收到消息就注销
        LocalBroadcastManager.getInstance(Foundation.getAppContext()).unregisterReceiver(this)
        when (intent.action) {
            ACTION_LOGIN_SUC -> callback.onSuccess(intent.getParcelableExtra("LoginEntity") as LoginEntity)
            else -> callback.onCancel()
        }
    }
}