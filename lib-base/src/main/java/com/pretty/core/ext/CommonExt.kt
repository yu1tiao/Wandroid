package com.pretty.core.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.text.Spanned
import androidx.core.content.getSystemService
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


/**
 * 复制文本到粘贴板
 */
fun Context.copyToClipboard(text: String, label: String = "JetpackMvvm") {
    val clipData = ClipData.newPlainText(label, text)
    getSystemService<ClipboardManager>()?.setPrimaryClip(clipData)
}

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}

inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline block: (T) -> Unit) {
    liveData.observe(this, Observer { block(it) })
}