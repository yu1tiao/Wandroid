package com.pretty.wandroid.user.login

import androidx.lifecycle.MutableLiveData
import com.pretty.core.base.BaseViewModel
import com.pretty.core.ext.launch
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.LoginReceiver
import com.pretty.core.util.showToast
import com.pretty.wandroid.user.service.LoginManager

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
        launch({
            model.login(username, password)
        }, {
            isLoginSuccess = true
            LoginManager.saveLoginEntity(it.data!!)
            LoginReceiver.sendLoginSuccess(it.data!!)
            loginSuccess.postValue(it.data)
        })
    }

    override fun onCleared() {
        super.onCleared()
        if (!isLoginSuccess) {
            LoginReceiver.sendLoginCancel()
        }
    }
}