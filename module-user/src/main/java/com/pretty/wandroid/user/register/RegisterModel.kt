package com.pretty.wandroid.user.register

import com.pretty.core.base.BaseModel
import com.pretty.core.http.FlatResp
import com.pretty.core.http.RetrofitManager
import com.pretty.core.http.check
import com.pretty.wandroid.user.api.UserApi


class RegisterModel : BaseModel() {

    private val api by lazy { RetrofitManager.obtainService(UserApi::class.java) }

    suspend fun register(userName: String, password: String): FlatResp {
        // retrofit 会自动添加withContext，这里虽然是在后台请求，但是不用手动添加
        return api.register(userName, password, password).check()
    }

}
