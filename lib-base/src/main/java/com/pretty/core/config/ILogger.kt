package com.pretty.core.config

interface ILogger {

    var globalTag: String
    var isDebug: Boolean

    fun print(level: Int, tag: String, message: String?)
}