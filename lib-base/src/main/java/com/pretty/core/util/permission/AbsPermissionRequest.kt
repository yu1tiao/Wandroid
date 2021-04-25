package com.pretty.core.util.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.pretty.core.util.sp.getSp

abstract class AbsPermissionRequest(internal val requestCode: Int) {

    private var firstRequestCallback: ((permissions: List<String>, Runnable) -> Unit)? = null
    private var rationaleCallback: ((Runnable) -> Unit)? = null
    private var grantedCallback: (() -> Unit)? = null
    private var deniedCallback: ((List<String>) -> Unit)? = null
    private var permissions: Array<out String>? = null
    private val sp by lazy { getSp("permission") }

    fun permissions(vararg permission: String): AbsPermissionRequest {
        this.permissions = permission
        return this
    }

    fun onGranted(callback: () -> Unit): AbsPermissionRequest {
        this.grantedCallback = callback
        return this
    }

    fun onDenied(callback: (List<String>) -> Unit): AbsPermissionRequest {
        this.deniedCallback = callback
        return this
    }

    /**
     * 第一次申请被拒绝后(没有选中总是拒绝)，第二次申请会回调这里，可以弹出对话框向用户解释为何需要此权限
     * 必须在回调里执行Runnable继续请求或者cancelRequest()取消权限请求
     *
     * ps: 有些厂商修改了shouldShowRequestPermissionRationale的逻辑，可能造成这个回调不调用
     */
    fun onRationale(callback: (Runnable) -> Unit): AbsPermissionRequest {
        this.rationaleCallback = callback
        return this
    }

    /**
     * 第一次申请某些权限时回调，可能需要弹框说明为什么要申请此权限
     * 通过callback回调第一次申请的 permissions回去
     *
     * 必须在回调里执行Runnable继续请求或者cancelRequest()取消权限请求
     */
    fun onFirstTimeRequest(callback: (permissions: List<String>, Runnable) -> Unit): AbsPermissionRequest {
        this.firstRequestCallback = callback
        return this
    }

    /**
     * onRationale回调中调用，取消申请权限
     */
    fun cancel() {
        PermissionManager.removeRequest(requestCode)
    }

    internal fun callGranted() {
        grantedCallback?.invoke()
    }

    internal fun callDenied(list: List<String>) {
        deniedCallback?.invoke(list)
    }

    private fun havePermissions(permissions: Array<out String>): Boolean {
        permissions.forEach {
            if (!checkSelfPermission(it)) return false
        }
        return true
    }

    /**
     * 这个方法在某些厂商手机上也不一定靠谱，比如oppo，检查权限一定返回true，而要到真正调用的时候才去申请。
     * 比如相机权限，一直返回true，要真正调用Camera.open()才会弹出权限申请框
     * 对于这部分厂商，只能自己去适配了，一定要适配可以骚操作，先尝试打开相机触发权限申请
     */
    private fun checkSelfPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            getContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    protected abstract fun getContext(): Context

    protected abstract fun shouldShowRequestPermissionRationale(permissions: Array<out String>): Boolean

    protected abstract fun requestEach(permissions: Array<out String>)

    fun start() {
        if (permissions == null) {
            throw RuntimeException("request permissions = null")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val runnable = Runnable {
                if (havePermissions(permissions!!)) {
                    callGranted()
                } else {
                    if (rationaleCallback != null && shouldShowRequestPermissionRationale(
                            permissions!!
                        )
                    ) {
                        rationaleCallback?.invoke {
                            requestEach(permissions!!)
                        }
                    } else {
                        requestEach(permissions!!)
                    }
                }
            }


            if (firstRequestCallback != null) {
                // 如果某权限是第一次申请，回调第一次申请的callback
                val firstRequest = permissions!!.filter {
                    isFirstRequest(it)
                }
                if (firstRequest.isEmpty()) {
                    runnable.run()
                } else {
                    firstRequestCallback?.invoke(firstRequest, runnable)
                    firstRequest.forEach {
                        saveFirstRequest(it)
                    }
                }
            } else {
                runnable.run()
            }
        } else {
            callGranted()
        }
    }

    private fun isFirstRequest(permission: String): Boolean {
        return sp.getBoolean(permission, true)
    }

    private fun saveFirstRequest(permission: String) {
        sp.edit().putBoolean(permission, false).apply()
    }
}