package com.pretty.core.util.sp

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Copyright (c) 2021 北京嗨学网教育科技股份有限公司 All rights reserved.
 *
 * @author yuli
 * @date 4/21/21
 * @description SharedPreferences委托
 *
 * 使用方法：
 *  var name by SpDelegate("key", "defaultValue", sp)
 */
class SpDelegate<T>(
    private val key: String? = null,
    private val defValue: T,
    private val prefs: SharedPreferences
) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        read(getKey(property))

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        save(getKey(property), value)

    private fun getKey(property: KProperty<*>) = if (key.isNullOrEmpty()) property.name else key

    private fun read(key: String): T = when (defValue) {
        is Int -> prefs.getInt(key, defValue)
        is Long -> prefs.getLong(key, defValue)
        is Float -> prefs.getFloat(key, defValue)
        is Boolean -> prefs.getBoolean(key, defValue)
        is String -> prefs.getString(key, defValue)
        else -> throw IllegalArgumentException("Unsupported type.")
    } as T

    private fun save(key: String, value: T) {
        prefs.edit().run {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type.")
            }
        }.apply()
    }

}