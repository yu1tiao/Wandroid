package com.pretty.wandroid.user.login

import androidx.lifecycle.MutableLiveData
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.safeLaunch
import com.pretty.core.ext.showToast
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.LoginReceiver

class LoginViewModel : BaseViewModel() {

    val loginSuccess = MutableLiveData<LoginEntity>()
    private var isLoginSuccess = false

    private val model by lazy { LoginModel() }

    fun btnLogin(username: String, password: String) {
        if (username.isEmpty()) {
            showToast("用户名为空")
            return
        }
        if (password.isEmpty()) {
            showToast("密码为空")
            return
        }

        safeLaunch({
            model.login(username, password)
        }, {
            isLoginSuccess = true
            LoginReceiver.sendLoginSuccess(it!!)
            loginSuccess.postValue(it)
        })
    }

    override fun onCleared() {
        super.onCleared()
        if (!isLoginSuccess) {
            LoginReceiver.sendLoginCancel()
        }
    }
}