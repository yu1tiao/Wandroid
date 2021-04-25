package com.pretty.core.base

import androidx.multidex.MultiDexApplication
import com.pretty.core.Foundation
import com.pretty.core.config.GlobalConfiguration

/**
 * @author yu
 * @date 2018/11/9
 */
abstract class BaseApplication : MultiDexApplication() {

    abstract var mConfiguration: GlobalConfiguration

    override fun onCreate() {
        super.onCreate()
        Foundation.init(this)
    }
}
