package com.pretty.wandroid.user.register

import androidx.databinding.ObservableField
import com.pretty.core.base.BaseViewModel
import com.pretty.core.util.SingleLiveEvent
import com.pretty.core.util.showToast


class RegisterViewModel : BaseViewModel() {

    val userName = ObservableField<String>()
    val passWord = ObservableField<String>()
    val rePassWord = ObservableField<String>()

    val registerSuccess = SingleLiveEvent<Void>()

    private val model by lazy { RegisterModel() }

    fun btnRegister() {
        if (rePassWord.get()!! != passWord.get()) {
            showToast("重复输入密码错误")
            return
        }

        launch({
            model.register(userName.get()!!, passWord.get()!!)
        }, success = {
            registerSuccess.call()
            showToast("注册成功!")
        }, showLoading = true)
    }

}