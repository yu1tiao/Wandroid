package com.pretty.core.util

import android.os.Parcelable
import com.pretty.core.Foundation
import com.tencent.mmkv.MMKV

/**
 * 腾讯开源的高效 key-value 存储库，可以替代系统SharedPreferences
 * MMKV本身也实现了SharedPreferences接口，可以无痛切换回去
 * @author yu
 * @date 2019/3/11
 */
object AppSPUtil {

    val sp: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    fun init() {
        MMKV.initialize(Foundation.getAppContext())
    }

    fun put(key: String, obj: Any) {
        when (obj) {
            is Int -> sp.encode(key, obj)
            is Long -> sp.encode(key, obj)
            is Float -> sp.encode(key, obj)
            is Double -> sp.encode(key, obj)
            is Boolean -> sp.encode(key, obj)
            is String -> sp.encode(key, obj)
            is Parcelable -> sp.encode(key, obj)
            else -> throw RuntimeException("not support")
        }
    }

    fun remove(key: String) = sp.remove(key)
    fun clear() = sp.clearAll()

    fun getString(key: String, defaultValue: String = "") = sp.decodeString(key, defaultValue)
    fun getInt(key: String, defaultValue: Int = -1) = sp.decodeInt(key, defaultValue)
    fun getLong(key: String, defaultValue: Long = -1L) = sp.decodeLong(key, defaultValue)
    fun getFloat(key: String, defaultValue: Float = 1F) = sp.decodeFloat(key, defaultValue)
    fun getBoolean(key: String, defaultValue: Boolean = false) = sp.decodeBool(key, defaultValue)
    fun getDouble(key: String, defaultValue: Double = 0.0) = sp.decodeDouble(key, defaultValue)

    inline fun <reified T : Parcelable> getParcelable(key: String, defaultValue: T? = null): T? {
        return sp.decodeParcelable(key, T::class.java, defaultValue)
    }

}

