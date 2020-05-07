package com.pretty.core.util.permission

import android.content.Context
import androidx.fragment.app.Fragment
import com.pretty.core.util.L

class RequestPermissionFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        L.i("${this.javaClass.name} is attached...")
    }

    override fun onDetach() {
        super.onDetach()
        L.i("${this.javaClass.name} is detached...")
    }

    private fun detach() {
        if (parentFragment != null) {
            parentFragment!!.childFragmentManager.beginTransaction()
                .detach(this@RequestPermissionFragment)
                .remove(this@RequestPermissionFragment)
                .commit()
        } else if (activity != null) {
            activity!!.supportFragmentManager.beginTransaction()
                .detach(this@RequestPermissionFragment)
                .remove(this@RequestPermissionFragment)
                .commit()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        detach()
    }

}