package com.pretty.core.config

import android.content.Context

interface ILogger {

    var globalTag: String
    var isDebug: Boolean

    fun init(context: Context)

    fun print(level: Int, tag: String, message: String?)
}