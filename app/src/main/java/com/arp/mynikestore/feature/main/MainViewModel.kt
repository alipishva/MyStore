package com.arp.mynikestore.feature.main

import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(productRepository : ProductRepository) : NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Product>> {
                override fun onSubscribe(d : Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t : List<Product>) {
                    productLiveData.value = t
                }

                override fun onError(e : Throwable) {
                    Timber.e(e)
                }

            })
    }
}