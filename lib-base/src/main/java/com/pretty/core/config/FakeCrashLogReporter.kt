package com.pretty.core.config

import com.pretty.core.util.CrashLogReporter

class FakeCrashLogReporter:CrashLogReporter {
    override fun log(priority: Int, tag: String?, message: String) {

    }

    override fun logWarning(t: Throwable) {
    }

    override fun logError(t: Throwable) {
    }
}