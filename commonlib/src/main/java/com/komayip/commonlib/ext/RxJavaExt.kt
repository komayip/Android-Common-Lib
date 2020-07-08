package com.komayip.commonlib.ext

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *   Useful Extensions for RxJava
 **/

/**
 * Add a disposable to a dispose bag
 *
 * @param disposedBag: a dispose bag
 */
fun Disposable.disposedBy(disposedBag: CompositeDisposable) {
    disposedBag.add(this)
}

/**
 * An extension for the clumsy background & main thread
 */
fun <T> Observable<T>.runInBackground(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onTerminateDetach()
}

fun <T> Observable<T>.onMainThread(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}