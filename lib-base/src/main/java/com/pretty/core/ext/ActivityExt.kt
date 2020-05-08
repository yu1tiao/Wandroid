package com.pretty.core.ext

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi


/**
 * 沉浸式系统UI，将系统UI(状态栏和导航栏)设为透明，并且主布局沉浸到系统UI下面。
 * @param [light] true-状态栏字体和图标为黑色，false-状态栏图标为白色
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.immersiveSystemUi(light: Boolean = true) {
    window.run {
        val mode = if (light) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or mode
        statusBarColor = Color.TRANSPARENT
        navigationBarColor = Color.TRANSPARENT
    }
}

/**
 * 设置状态栏颜色
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

/**
 * 设置导航栏颜色
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setNavigationBarColor(color: Int) {
    window.navigationBarColor = color
}
