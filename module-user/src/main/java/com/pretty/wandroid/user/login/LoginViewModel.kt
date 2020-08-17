package com.pretty.wandroid.user.login

import androidx.databinding.ObservableField
import com.pretty.core.base.BaseViewModel
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.router.service.LoginReceiver
import com.pretty.core.util.SingleLiveEvent
import com.pretty.wandroid.user.service.LoginManager

class LoginViewModel : BaseViewModel() {

    val username = ObservableField<String>()
    val password = ObservableField<String>()
    val loginSuccess = SingleLiveEvent<LoginEntity>()

    private var isLoginSuccess = false

    private val model by lazy { LoginModel() }

    fun btnLogin() {
        launch({
            model.login(username.get()!!, password.get()!!)
        }, {
            LoginManager.saveLoginEntity(it.data!!)
            LoginReceiver.sendLoginSuccess(it.data!!)
            loginSuccess.postValue(it.data)
        }, showLoading = true)
    }

    override fun onCleared() {
        super.onCleared()
        if (!isLoginSuccess) {
            LoginReceiver.sendLoginCancel()
        }
    }
}