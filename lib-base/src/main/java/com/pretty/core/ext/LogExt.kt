package com.pretty.core.ext

import android.util.Log
import com.pretty.core.Foundation


fun Any?.logV(tag: String? = null) {
    printLog(Log.VERBOSE, tag)
}

fun Any?.logD(tag: String? = null) {
    printLog(Log.DEBUG, tag)
}

fun Any?.logI(tag: String? = null) {
    printLog(Log.INFO, tag)
}

fun Any?.logW(tag: String? = null) {
    printLog(Log.WARN, tag)
}

fun Any?.logE(tag: String? = null) {
    printLog(Log.ERROR, tag)
}

private fun Any?.printLog(level: Int, tag: String? = null) {
    if (this == null) return
    Foundation.getGlobalConfig().logger.let {
        val finalTag = if (tag.isNullOrEmpty()) it.globalTag else tag
        it.print(level, finalTag, this.toString())
    }
}