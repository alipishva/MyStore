package com.arp.mynikestore.common

import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class NikeCompletableObserver(private val compositeDisposable : CompositeDisposable) : CompletableObserver {
    override fun onError(e : Throwable) {
        EventBus.getDefault().post(NikeExceptionMapper.map(e))
    }

    override fun onSubscribe(d : Disposable) {
        compositeDisposable.add(d)
    }


}