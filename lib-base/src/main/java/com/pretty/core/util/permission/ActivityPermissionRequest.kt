package com.pretty.core.util.permission

import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity


class ActivityPermissionRequest(private val activity: FragmentActivity, requestCode: Int) :
    AbsPermissionRequest(requestCode) {

    override fun getContext(): Context {
        return activity
    }

    override fun requestEach(permissions: Array<out String>) {
        with(activity.supportFragmentManager) {
            val f = RequestPermissionFragment()
            beginTransaction().add(f, "requestPermissionFromActivity").commit()
            executePendingTransactions()
            f.requestPermissions(permissions, requestCode)
        }
    }

    override fun shouldShowRequestPermissionRationale(permissions: Array<out String>): Boolean {
        permissions.forEach {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                return true
            }
        }
        return false
    }
}