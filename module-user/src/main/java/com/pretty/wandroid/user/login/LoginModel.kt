package com.pretty.wandroid.user.login

import com.pretty.core.base.BaseModel
import com.pretty.core.ext.safeApi
import com.pretty.core.http.*
import com.pretty.core.router.entity.LoginEntity
import com.pretty.wandroid.user.api.UserApi


class LoginModel : BaseModel() {

    private val api by lazy { RetrofitManager.obtainService(UserApi::class.java) }

    suspend fun login(username: String, password: String): ApiResult<LoginEntity> {
        return safeApi {
            api.login(username, password)
        }
    }

}
