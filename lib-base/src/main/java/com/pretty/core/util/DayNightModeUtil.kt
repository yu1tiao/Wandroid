package com.pretty.core.util

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

/**
 * 黑夜模式
 */

fun isNightMode(context: Context): Boolean {
    val mode = context.resources.configuration.uiMode and UI_MODE_NIGHT_MASK
    return mode == UI_MODE_NIGHT_YES
}

fun setNightMode(isNightMode: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (isNightMode) MODE_NIGHT_YES else MODE_NIGHT_NO
    )
}
