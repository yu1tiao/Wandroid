package com.pretty.core.util.permission

import android.content.Context
import androidx.fragment.app.Fragment


class FragmentPermissionRequest(private val fragment: Fragment, requestCode: Int) :
    AbsPermissionRequest(requestCode) {

    override fun getContext(): Context {
        return fragment.activity
            ?: throw NullPointerException("your fragment has not attach to activity")
    }

    override fun requestEach(permissions: Array<out String>) {
        fragment.requestPermissions(permissions, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(permissions: Array<out String>): Boolean {
        permissions.forEach {
            if (fragment.shouldShowRequestPermissionRationale(it)) {
                return true
            }
        }
        return false
    }
}
