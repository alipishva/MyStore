package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductRepository {
    fun getProducts(sort : Int) : Single<List<Product>>
    fun getFavoriteProducts() : Single<List<Product>>
    fun addToFavorite(Product : Product) : Completable
    fun deleteFromFavorite(Product : Product) : Completable
}