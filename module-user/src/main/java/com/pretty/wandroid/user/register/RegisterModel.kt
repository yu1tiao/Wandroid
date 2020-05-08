package com.pretty.wandroid.user.register

import com.pretty.core.base.BaseModel
import com.pretty.core.http.FlatResp
import com.pretty.core.http.RetrofitManager
import com.pretty.wandroid.user.api.UserApi


class RegisterModel : BaseModel() {

    private val api by lazy { RetrofitManager.obtainService(UserApi::class.java) }

    suspend fun register(userName: String, password: String): FlatResp {
        return requestHttp {
            api.register(userName, password, password)
        }
    }

}
