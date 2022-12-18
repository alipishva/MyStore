package com.arp.mynikestore.common

import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

abstract class NikeSingleObserver<T>(private val compositeDisposable : CompositeDisposable) : SingleObserver<T> {
    override fun onSubscribe(d : Disposable) {
        compositeDisposable.add(d)
    }

    override fun onError(e : Throwable) {
        Timber.e(e.toString())
    }
}