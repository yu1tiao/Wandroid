package com.pretty.core.util

import androidx.lifecycle.*

/**
 * 用于app进入后台和回到前台的回调
 */
object AppLifeObserver : LifecycleObserver {

    var isForeground = MutableLiveData(false)

    fun register() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        isForeground.value = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        isForeground.value = false
    }

}