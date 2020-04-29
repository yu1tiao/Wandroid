package com.pretty.core.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * @author mathew
 * @date 2019/5/7
 */

inline fun <reified T> Any.safeCast(action: (T) -> Unit) {
    if (this is T) action(this)
}

fun <T> T?.safeValue(valueIfNull: T): T {
    return this ?: valueIfNull
}

inline fun <reified T> List<T>?.toNonNullList(): List<T> {
    return this ?: emptyList()
}

fun bundleOf(vararg pairs: Pair<String, Any> = emptyArray()): Bundle {
    return if (pairs.isNotEmpty())
        pairs.toBundle()
    else
        Bundle()
}

fun Array<out Pair<String, Any>>.toBundle(): Bundle {
    return Bundle().apply {
        forEach {
            val key = it.first
            when (val value = it.second) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Double -> putDouble(key, value)
                is Long -> putLong(key, value)
                is Parcelable -> putParcelable(key, value)
                is Serializable -> putSerializable(key, value)
                else -> throw RuntimeException("un support type")
            }
        }
    }
}

fun <T : Activity> Context.jump2Activity(
    clazz: Class<T>,
    bundle: Bundle? = null
) {
    Intent(this, clazz)
        .apply {
            if (bundle != null && !bundle.isEmpty)
                putExtras(bundle)
            if (this@jump2Activity !is Activity)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.run {
            startActivity(this)
        }
}