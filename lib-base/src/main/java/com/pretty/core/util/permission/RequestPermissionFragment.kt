package com.pretty.core.util.permission

import android.content.Context
import androidx.fragment.app.Fragment
import com.pretty.core.ext.logi

class RequestPermissionFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        "${this.javaClass.name} is attached...".logi()
    }

    override fun onDetach() {
        super.onDetach()
        "${this.javaClass.name} is detached...".logi()
    }

    private fun detach() {
        if (parentFragment != null) {
            requireParentFragment().childFragmentManager.beginTransaction()
                .detach(this@RequestPermissionFragment)
                .remove(this@RequestPermissionFragment)
                .commit()
        } else if (activity != null) {
            requireActivity().supportFragmentManager.beginTransaction()
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