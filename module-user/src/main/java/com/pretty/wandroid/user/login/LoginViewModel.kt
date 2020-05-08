package com.pretty.wandroid.user.login

import androidx.databinding.ObservableField
import com.pretty.core.base.BaseViewModel
import com.pretty.core.service.entity.LoginEntity
import com.pretty.core.util.SingleLiveEvent
import com.pretty.wandroid.user.LoginManager


class LoginViewModel : BaseViewModel() {

    val userName = ObservableField<String>()
    val passWord = ObservableField<String>()
    val loginSuccess = SingleLiveEvent<LoginEntity>()

    private val model by lazy { LoginModel() }

    fun btnLogin() {
        launch({
            model.login(userName.get()!!, passWord.get()!!)
        }, {
            loginSuccess.postValue(it.data)
            LoginManager.loginSuccess(it.data!!)
        }, showLoading = true)
    }
}