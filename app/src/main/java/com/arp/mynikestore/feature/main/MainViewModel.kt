package com.arp.mynikestore.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.data.Banner
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.BannerRepository
import com.arp.mynikestore.data.repo.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(
    productRepository : ProductRepository , private val bannerRepository : BannerRepository
) : NikeViewModel() {

    private val _productLiveData = MutableLiveData<List<Product>>()
    val productLiveData : LiveData<List<Product>>
        get() = _productLiveData

    private val _bannerLiveData = MutableLiveData<List<Banner>>()
    val bannerLiveData : LiveData<List<Banner>>
        get() = _bannerLiveData

    init {
        progressBraLiveData.value = true
        productRepository.getProducts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBraLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t : List<Product>) {
                    _productLiveData.value = t
                }
            })

        bannerRepository.getBannerList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t : List<Banner>) {
                    _bannerLiveData.value = t
                }
            })
    }
}