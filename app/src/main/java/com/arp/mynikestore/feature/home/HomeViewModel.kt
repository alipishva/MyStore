package com.arp.mynikestore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeCompletableObserver
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.data.Banner
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.SORT_LATEST_PRODUCT
import com.arp.mynikestore.data.SORT_POPULAR_PRODUCT
import com.arp.mynikestore.data.repo.banner.BannerRepository
import com.arp.mynikestore.data.repo.product.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(private val productRepository : ProductRepository , private val bannerRepository : BannerRepository) : NikeViewModel() {

    private val _productLatestLiveData = MutableLiveData<List<Product>>()
    val productLatestLiveData : LiveData<List<Product>>
        get() = _productLatestLiveData

    private val _bannerLiveData = MutableLiveData<List<Banner>>()
    val bannerLiveData : LiveData<List<Banner>>
        get() = _bannerLiveData

    private val _productPopularLiveData = MutableLiveData<List<Product>>()
    val productPopularLiveData : LiveData<List<Product>>
        get() = _productPopularLiveData

    init {
        progressBarLiveData.value = true

        productRepository.getProducts(SORT_LATEST_PRODUCT).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t : List<Product>) {
                    _productLatestLiveData.value = t
                }
            })

        productRepository.getProducts(SORT_POPULAR_PRODUCT).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t : List<Product>) {
                    _productPopularLiveData.value = t
                }
            })

        bannerRepository.getBannerList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t : List<Banner>) {
                    _bannerLiveData.value = t
                }
            })
    }

    fun addProductToFavorites(product : Product) {
        if (product.isFavorite) {
            productRepository.deleteFromFavorite(product).subscribeOn(Schedulers.io()).subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        } else {
            productRepository.addToFavorite(product).subscribeOn(Schedulers.io()).subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
        }
    }

}