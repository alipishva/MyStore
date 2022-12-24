package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ProductDataSource {

    fun getProducts(sort:Int) : Single<List<Product>>
    fun getFavoriteProducts() : Single<List<Product>>
    fun addToFavorite() : Completable
    fun deleteFromFavorite() : Completable
}