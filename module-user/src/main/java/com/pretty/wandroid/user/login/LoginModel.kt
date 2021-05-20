package com.pretty.wandroid.user.login

import com.pretty.core.base.BaseModel
import com.pretty.core.ext.safeApi
import com.pretty.core.ext.toFlow
import com.pretty.core.http.ApiResult
import com.pretty.core.http.RetrofitManager
import com.pretty.core.router.entity.LoginEntity
import com.pretty.wandroid.user.api.UserApi
import kotlinx.coroutines.flow.Flow


class LoginModel : BaseModel() {

    private val api by lazy { RetrofitManager.obtainService(UserApi::class.java) }

    suspend fun login(username: String, password: String): ApiResult<LoginEntity> {
        return safeApi {
            api.login(username, password)
        }
    }

    suspend fun register(username: String, password: String): Flow<LoginEntity> {
        return api.register(username, password, password).toFlow()
    }

}
