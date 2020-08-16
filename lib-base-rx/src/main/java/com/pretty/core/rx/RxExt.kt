package com.pretty.core.rx

import autodispose2.*
import com.pretty.core.Foundation
import com.pretty.core.arch.ILoadable
import com.pretty.core.http.Resp
import com.pretty.core.http.check
import com.pretty.core.util.runOnMainThread
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 检查网络请求
 */

private class HttpFunction<T : Resp<*>> : Function<T, T> {
    override fun apply(t: T): T {
        return t.check()
    }
}

fun <T : Resp<*>> Observable<T>.checkHttpResp(): Observable<T> {
    return this.map(HttpFunction())
}

fun <T : Resp<*>> Flowable<T>.checkHttpResp(): Flowable<T> {
    return this.map(HttpFunction())
}

fun <T : Resp<*>> Single<T>.checkHttpResp(): Single<T> {
    return this.map(HttpFunction())
}

fun <T : Resp<*>> Maybe<T>.checkHttpResp(): Maybe<T> {
    return this.map(HttpFunction())
}

/**
 * 应用默认的错误处理
 */
fun <T> Observable<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> Flowable<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> Single<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> Maybe<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun Completable.apiSubscribe(
    onNext: () -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> ObservableSubscribeProxy<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> FlowableSubscribeProxy<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> SingleSubscribeProxy<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun <T> MaybeSubscribeProxy<T>.apiSubscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

fun CompletableSubscribeProxy.apiSubscribe(
    onNext: () -> Unit,
    onError: ((Throwable) -> Unit) = Foundation.getGlobalConfig().errorHandler
) = subscribe(onNext, onError)

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