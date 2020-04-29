package com.pretty.core.util.permission

import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.AppUtils

/**
 * 可以在BaseActivity和BaseFragment重写onRequestPermissionsResult,
 * 添加代码:PermissionManager.onRequestResult(requestCode, permissions, grantResults)进行全局的处理
 * 也可以在具体的页面处理
 *
 * 用法：
    PermissionManager.with(this)
        .permissions(Manifest.permission.CAMERA)
        .onRationale {  } // 可选，第一次拒绝后，第二次回调这里，可以弹窗解释为何需要此权限，内部必须调用继续或者取消权限请求
        .onGranted {  }// 权限授予
        .onDenied {  }// 权限拒绝
        .start()
 */
object PermissionManager {

    private const val INDEX_OFFSET = 123
    private val requestMap: SparseArray<AbsPermissionRequest> = SparseArray(4)

    @JvmStatic
    fun with(activity: FragmentActivity): AbsPermissionRequest {
        val requestCode = INDEX_OFFSET + requestMap.size()
        val request = ActivityPermissionRequest(activity, requestCode)
        requestMap.put(requestCode, request)
        return request
    }

    @JvmStatic
    fun with(fragment: Fragment): AbsPermissionRequest {
        val requestCode = INDEX_OFFSET + requestMap.size()
        val request = FragmentPermissionRequest(fragment, requestCode)
        requestMap.put(requestCode, request)
        return request
    }

    @JvmStatic
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val request = requestMap[requestCode] ?: return

        var allGranted = true
        val deniedList: MutableList<String> = mutableListOf()

        grantResults.forEachIndexed { index, result ->
            if (result != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permissions[index])
                allGranted = false
            }
        }

        if (allGranted) {
            request.callGranted()
        } else {
            request.callDenied(deniedList)
        }

        request.cancelRequest()
    }

    internal fun removeRequest(requestCode: Int) {
        requestMap.remove(requestCode)
    }
}
