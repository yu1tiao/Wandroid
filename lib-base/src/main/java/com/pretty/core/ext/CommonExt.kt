package com.pretty.core.ext

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.Serializable

/**
 * @author mathew
 * @date 2019/5/7
 */

inline fun <reified T> Any.safeCast(crossinline action: (T) -> Unit) {
    if (this is T) action(this)
}

fun <T> T?.safeValue(valueIfNull: T): T {
    return this ?: valueIfNull
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

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this, Observer { block(it) })
}