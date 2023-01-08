package com.arp.mynikestore.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.R
import com.arp.mynikestore.common.NikeCompletableObserver
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.ProductRepository
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductListViewModel(var sort : Int , private val productRepository : ProductRepository) : NikeViewModel() {

    private val _productListLiveData = MutableLiveData<List<Product>>()
    val productListLiveData : LiveData<List<Product>>
        get() = _productListLiveData

    val sortTypeListLiveData = MutableLiveData<Int>()

    private val sortTitle = arrayOf(R.string.latest , R.string.popular , R.string.asce , R.string.desc)

    init {
        getProducts()
        sortTypeListLiveData.value = sortTitle[sort]
    }


    private fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProducts(sort).asyncNetworkRequest().doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t : List<Product>) {
                    _productListLiveData.value = t
                }
            })


    }

    fun onSelectedSortChangeByUser(sort : Int) {
        this.sort = sort
        this.sortTypeListLiveData.value = sortTitle[sort]
        getProducts()
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