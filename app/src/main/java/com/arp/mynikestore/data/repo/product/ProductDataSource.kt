package com.arp.mynikestore.data.repo.product

import com.arp.mynikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductDataSource {

    fun getProducts(sort : Int) : Single<List<Product>>
    fun getFavoriteProducts() : Single<List<Product>>
    fun addToFavorite(Product : Product) : Completable
    fun deleteFromFavorite(Product : Product) : Completable
}