package com.pretty.wandroid.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.toastErrorHandler
import com.pretty.core.ext.showToast
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.LoginReceiver
import kotlinx.coroutines.launch

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

        viewModelScope.launch {
            model.login(username, password)
                .onSuccess {
                    isLoginSuccess = true
                    LoginReceiver.sendLoginSuccess(it!!)
                    loginSuccess.postValue(it)
                }
                .onError(toastErrorHandler)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!isLoginSuccess) {
            LoginReceiver.sendLoginCancel()
        }
    }
}