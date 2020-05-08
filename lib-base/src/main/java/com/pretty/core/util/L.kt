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
    fun v(t: Throwable) = Timber.v(t)

    fun d(content: String) = Timber.d(content)
    fun d(t: Throwable) = Timber.d(t)

    fun i(content: String) = Timber.i(content)
    fun i(t: Throwable) = Timber.i(t)

    fun w(content: String) = Timber.w(content)
    fun w(t: Throwable) = Timber.w(t)

    fun e(content: String) = Timber.e(content)
    fun e(t: Throwable) = Timber.e(t)

}

class CrashReportingTree(private val reporter: CrashLogReporter) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

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