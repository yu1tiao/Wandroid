package com.pretty.core.util

import android.util.Log
import timber.log.Timber

object L {

    fun init(isDebug: Boolean, reporter: CrashLogReporter) {
        if (isDebug)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(CrashReportingTree(reporter))

        i("initLogger success, isDebug = $isDebug")
    }

    fun v(content: String) = Timber.v(content)

    fun d(content: String) = Timber.d(content)

    fun i(content: String) = Timber.i(content)

    fun w(content: String) = Timber.w(content)

    fun e(content: String) = Timber.e(content)
    fun e(t: Throwable) = Timber.e(t)

}

class CrashReportingTree(private val reporter: CrashLogReporter) : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority == Log.WARN || priority == Log.ERROR || priority == Log.ASSERT
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        reporter.log(priority, tag, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                reporter.logError(t)
            } else if (priority == Log.WARN) {
                reporter.logWarning(t)
            }
        }
    }
}

interface CrashLogReporter {
    fun log(priority: Int, tag: String?, message: String)
    fun logWarning(t: Throwable)
    fun logError(t: Throwable)
}