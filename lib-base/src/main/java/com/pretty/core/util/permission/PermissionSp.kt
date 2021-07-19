package com.pretty.core.util.permission

import com.pretty.core.util.sp.BaseStorage

/**
 * Copyright (c) 2021 $ All rights reserved.
 *
 * @author matthew
 * @date 2021/7/19
 * @description PermissionSp
 */
class PermissionSp : BaseStorage("PermissionSp") {

    fun isFirstRequest(permission: String): Boolean {
        return prefs.getBoolean(permission, true)
    }

    fun saveFirstRequest(permission: String) {
        prefs.edit().putBoolean(permission, false).apply()
    }
}