package com.pretty.core.config

import timber.log.Timber

class FakeLogTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

    }
}