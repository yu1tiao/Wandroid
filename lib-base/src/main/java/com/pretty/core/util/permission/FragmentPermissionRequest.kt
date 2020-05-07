package com.pretty.core.util.permission

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class FragmentPermissionRequest(private val fragment: Fragment, requestCode: Int) :
    AbsPermissionRequest(requestCode) {

    override fun getContext(): Context {
        return fragment.activity
            ?: throw NullPointerException("$fragment has not attach to activity")
    }

    override fun requestEach(permissions: Array<out String>) {
        val fragmentManager: FragmentManager = when {
            fragment.parentFragment != null -> fragment.parentFragment!!.childFragmentManager
            fragment.activity != null -> fragment.activity!!.supportFragmentManager
            else -> throw RuntimeException("$fragment has not attach to activity")
        }
        with(fragmentManager) {
            val f = RequestPermissionFragment()
            beginTransaction().add(f, "requestPermissionFromFragment").commit()
            executePendingTransactions()
            f.requestPermissions(permissions, requestCode)
        }
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