package com.pretty.core.http

import android.os.Build
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加一些默认header
 */
class CommonHeaderInterceptor : Interceptor {

    private val appName = AppUtils.getAppName()
    private val versionName = AppUtils.getAppVersionName()
    private val deviceId = DeviceUtils.getUniqueDeviceId()

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .addHeader("X-Platform", "Android")
            .addHeader("X-App-Version", versionName)
            .addHeader("X-OS-Version", Build.VERSION.SDK_INT.toString())
            .addHeader("X-Model", Build.MODEL)
            .addHeader("X-Device-Id", deviceId)
            .addHeader("X-App-Id", appName)

        return chain.proceed(requestBuilder.build())
    }
}