package com.pretty.core.arch

import android.app.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 绑定LifecycleOwner，在销毁时自动销毁添加的对象，如Dialog、Disposable、Destroyable
 */
interface IDisposableManager {

    fun init(lifecycleOwner: LifecycleOwner)

//    fun addDisposable(disposable: Disposable)

    fun handleDialog(dialog: Dialog)

    fun addDestroyable(destroyable: Destroyable)

    fun setOnDestroyListener(callback: () -> Unit)
}

interface Destroyable {
    fun destroy()
}

class DisposableManager : IDisposableManager, LifecycleObserver {

//    private val compositeDisposable by lazy { CompositeDisposable() }
    private var mDestroyCallback: (() -> Unit)? = null
    private val mDialogs: MutableList<Dialog> = ArrayList()
    private val mDestroyable: MutableList<Destroyable> = ArrayList()


    override fun init(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
    }

//    override fun addDisposable(disposable: Disposable) {
//        compositeDisposable.add(disposable)
//    }

    override fun handleDialog(dialog: Dialog) {
        mDialogs.add(dialog)
    }

    override fun addDestroyable(destroyable: Destroyable) {
        mDestroyable.add(destroyable)
    }

    override fun setOnDestroyListener(callback: () -> Unit) {
        mDestroyCallback = callback
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
//        compositeDisposable.dispose()
//        compositeDisposable.clear()

        mDialogs.forEach { if (it.isShowing) it.dismiss() }
        mDialogs.clear()

        mDestroyable.forEach { it.destroy() }
        mDestroyable.clear()

        mDestroyCallback?.invoke()
    }
}