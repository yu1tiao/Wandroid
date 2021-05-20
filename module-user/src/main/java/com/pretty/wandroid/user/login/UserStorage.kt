package com.pretty.wandroid.user.login

import com.google.gson.Gson
import com.pretty.core.router.entity.LoginEntity
import com.pretty.core.util.sp.BaseStorage

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 2021/5/20
 * @description UserStorage
 */
object UserStorage : BaseStorage("USER_STORAGE") {

    var isLogin: Boolean by sp(false)
    var userJsonString: String by sp("")

    fun getUser(): LoginEntity? {
        return Gson().fromJson(userJsonString, LoginEntity::class.java)
    }
}