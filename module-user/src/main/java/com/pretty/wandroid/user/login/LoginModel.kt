package com.pretty.wandroid.user.login

import com.pretty.core.base.BaseModel
import com.pretty.core.http.RetrofitManager
import com.pretty.core.service.entity.LoginEntity
import com.pretty.wandroid.user.WandroidResp
import com.pretty.wandroid.user.api.UserApi


class LoginModel : BaseModel() {

    private val api by lazy { RetrofitManager.obtainService(UserApi::class.java) }

    suspend fun login(username: String, password: String): WandroidResp<LoginEntity> {
        return requestHttp {
            api.login(username, password)
        }
    }

}
