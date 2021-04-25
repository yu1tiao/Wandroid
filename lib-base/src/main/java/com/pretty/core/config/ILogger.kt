package com.pretty.core.config

import android.util.Log

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 4/15/21
 * @description Logger
 */
interface ILogger  {

    var globalTag: String
    var isDebug: Boolean

    fun print(level: Int, tag: String, message: String?)
}

val defaultLogger = object : ILogger {

    override var globalTag: String = "HxLogger"
    override var isDebug: Boolean = true

    override fun print(level: Int, tag: String, message: String?) {
        if (!isDebug || message.isNullOrEmpty()) return
        when (level) {
            Log.VERBOSE -> Log.v(tag, message)
            Log.DEBUG -> Log.d(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            Log.ERROR -> Log.e(tag, message)
            else -> {
            }
        }
    }
}