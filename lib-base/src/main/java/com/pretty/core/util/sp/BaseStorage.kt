package com.pretty.core.util.sp

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 4/21/21
 * @description BaseStorage
 *
 * 使用方法：
 * 如下声明的变量将自动存入sp，key默认会直接取变量名，也可以传入自定义的key
 *   var x by sp(1)
 *   var z by sp("qwe", key)
 */
abstract class BaseStorage(private val prefName: String) {

    // 腾讯MMKV实现了SharedPreferences，效率更高、支持多进程且没有sp的线程ANR问题
    // 如果有问题，只需修改一句代码就可以快速切回原生
    val prefs by lazy {
        initSp(prefName)
    }

    protected fun <T> sp(defaultValue: T, key: String? = null): SpDelegate<T> {
        return SpDelegate(key = key, defValue = defaultValue, prefs = prefs)
    }

    open fun initSp(prefName: String): SharedPreferences {
        return MMKV.mmkvWithID(prefName)!!
    }
}