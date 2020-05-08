package com.pretty.core.rx

import autodispose2.*
import com.pretty.core.Foundation
import com.pretty.core.arch.ILoadable
import com.pretty.core.http.FlatResp
import com.pretty.core.http.check
import com.pretty.core.util.runOnMainThread
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 检查网络请求
 */

private class HttpFunction<T : FlatResp> : Function<T, T> {
    override fun apply(t: T): T {
        return t.check()
    }
}

fun <T : FlatResp> Observable<T>.checkHttpResp(): Observable<T> {
    return this.map(HttpFunction())
}

fun <T : FlatResp> Flowable<T>.checkHttpResp(): Flowable<T> {
    return this.map(HttpFunction())
}

fun <T : FlatResp> Single<T>.checkHttpResp(): Single<T> {
    return this.map(HttpFunction())
}

fun <T : FlatResp> Maybe<T>.checkHttpResp(): Maybe<T> {
    return this.map(HttpFunction())
}

/**
 * 应用默认的错误处理
 */
inline fun <T> Observable<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun <T> Flowable<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun <T> Single<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun <T> Maybe<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun Completable.apiSubscribe(
    crossinline onNext: () -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext() }, onError)

inline fun <T> ObservableSubscribeProxy<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun <T> FlowableSubscribeProxy<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun <T> SingleSubscribeProxy<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun <T> MaybeSubscribeProxy<T>.apiSubscribe(
    crossinline onNext: (T) -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext(it) }, onError)

inline fun CompletableSubscribeProxy.apiSubscribe(
    crossinline onNext: () -> Unit,
    noinline onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe({ onNext() }, onError)

/**
 * 切换线程
 */
fun <T> Observable<T>.ioToMain(): Observable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.ioToMain(): Flowable<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.ioToMain(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.ioToMain(): Maybe<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.ioToMain(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 * 订阅时显示一个加载进度，完成后隐藏
 */
fun <T> Observable<T>.showLoading(loadable: ILoadable, message: String? = null): Observable<T> =
    doOnSubscribe {
        runOnMainThread { loadable.showLoading(message) }
    }.doFinally {
        runOnMainThread { loadable.hideLoading() }
    }

fun <T> Flowable<T>.showLoading(loadable: ILoadable, message: String? = null): Flowable<T> =
    doOnSubscribe {
        runOnMainThread { loadable.showLoading(message) }
    }.doFinally {
        runOnMainThread { loadable.hideLoading() }
    }

fun <T> Single<T>.showLoading(loadable: ILoadable, message: String? = null): Single<T> =
    doOnSubscribe {
        runOnMainThread { loadable.showLoading(message) }
    }.doFinally {
        runOnMainThread { loadable.hideLoading() }
    }

fun <T> Maybe<T>.showLoading(loadable: ILoadable, message: String? = null): Maybe<T> =
    doOnSubscribe {
        runOnMainThread { loadable.showLoading(message) }
    }.doFinally {
        runOnMainThread { loadable.hideLoading() }
    }

fun Completable.showLoading(loadable: ILoadable, message: String? = null): Completable =
    doOnSubscribe {
        runOnMainThread { loadable.showLoading(message) }
    }.doFinally {
        runOnMainThread { loadable.hideLoading() }
    }