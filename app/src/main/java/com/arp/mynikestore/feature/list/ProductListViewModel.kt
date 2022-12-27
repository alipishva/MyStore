package com.arp.mynikestore.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.ProductRepository

class ProductListViewModel(val sort : Int , val productRepository : ProductRepository) : NikeViewModel() {

    private val _productListLiveData = MutableLiveData<List<Product>>()
    val productListLiveData : LiveData<List<Product>>
        get() = _productListLiveData

    init {

    }


    fun getProduct() : Unit {
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t : List<Product>) {
                    TODO("Not yet implemented")
                }
            })


    }
}