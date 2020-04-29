package com.pretty.core.util

import android.util.Log
import timber.log.Timber

object L {

    fun initLogger(isDebug: Boolean = true, reporter: CrashReporter = CrashReporter) {
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

}

class CrashReportingTree(private val reporter: CrashReporter) : Timber.Tree() {

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

interface CrashReporter {
    fun log(priority: Int, tag: String?, message: String)
    fun logWarning(t: Throwable)
    fun logError(t: Throwable)

    companion object : CrashReporter {
        override fun log(priority: Int, tag: String?, message: String) {

        }

        override fun logWarning(t: Throwable) {

        }

        override fun logError(t: Throwable) {

        }
    }
}