package com.pretty.core.rx

import autodispose2.lifecycle.CorrespondingEventsFunction
import autodispose2.lifecycle.LifecycleEndedException
import autodispose2.lifecycle.LifecycleScopeProvider
import com.pretty.core.base.BaseViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * 实现 {@link LifecycleScopeProvider}，用于AutoDispose框架管理RxJava的生命周期，在{@code onCleared}时取消订阅
 *
 * Observable.just("test")
 *           .autoDisposable(this)
 *           .subscribe{ //...}
 *
 * @author yu
 * @date 2018/10/29
 */
open class BaseViewModelRx : BaseViewModel(),
    LifecycleScopeProvider<BaseViewModelRx.ViewModelEvent> {

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }

    companion object {
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException("Cannot bind to ViewModel lifecycle after onCleared.")
            }
        }
    }
}