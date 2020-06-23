package com.pretty.core.router

object RC {
    const val SCHEME = "wangx"
    const val HOST = "page"
    const val SCHEME_HOST = "$SCHEME://$HOST"

    /////////////////////service////////////////////////
    const val ACCOUNT_SERVICE = "/account_service"

    /////////////////////page////////////////////////
    const val WANDROID_LOGIN_ACTIVITY = "/wandroid_login_activity"
    const val WANDROID_HOME_ACTIVITY = "/wandroid_home_activity"
    const val WANDROID_COLLECT_ACTIVITY = "/wandroid_collect_activity"


}

fun String.insertSchemeHost(): String {
    return "${RC.SCHEME_HOST}$this"
}
