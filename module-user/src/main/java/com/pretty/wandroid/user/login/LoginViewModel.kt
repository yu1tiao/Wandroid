package com.pretty.wandroid.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pretty.core.base.BaseViewModel
import com.pretty.core.config.toastErrorHandler
import com.pretty.core.ext.showToast
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.LoginReceiver
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    val loginSuccess = MutableLiveData<LoginEntity>()

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
                    UserStorage.userJsonString = Gson().toJson(it!!)
                    UserStorage.isLogin = true
                    LoginReceiver.sendLoginSuccess(it)
                    loginSuccess.postValue(it)
                }
                .onError(toastErrorHandler)

        }
    }

    fun register(username: String, password: String, rePassword: String) {
        if (username.isEmpty()) {
            showToast("用户名为空")
            return
        }
        if (password.isEmpty()) {
            showToast("密码为空")
            return
        }
        if (password != rePassword) {
            showToast("输入密码不一致")
            return
        }
        viewModelScope.launch {
            showLoading()
            model.register(username, password)
                .catch { toastErrorHandler(it) }
                .collectLatest {
                    UserStorage.userJsonString = Gson().toJson(it)
                    UserStorage.isLogin = true
                    LoginReceiver.sendLoginSuccess(it)
                    loginSuccess.postValue(it)
                }
            hideLoading()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!UserStorage.isLogin) {
            LoginReceiver.sendLoginCancel()
        }
    }
}