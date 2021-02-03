package com.pretty.core.ext

import timber.log.Timber
import kotlin.properties.Delegates


private var LOG_TAG by Delegates.notNull<String>()

fun initLogger(isDebug: Boolean, tag: String, releaseTree: Timber.Tree) {
    LOG_TAG = tag
    if (isDebug) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(releaseTree)
    }
}

fun String.logv(tag: String = LOG_TAG) {
    Timber.tag(tag).v(this)
}

fun String.logd(tag: String = LOG_TAG) {
    Timber.tag(tag).d(this)
}

fun String.logi(tag: String = LOG_TAG) {
    Timber.tag(tag).i(this)
}

fun String.logw(tag: String = LOG_TAG) {
    Timber.tag(tag).w(this)
}

fun String.loge(tag: String = LOG_TAG) {
    Timber.tag(tag).e(this)
}

fun Throwable.loge(tag: String = LOG_TAG) {
    Timber.tag(tag).e(this)
}