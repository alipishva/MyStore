package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductLocalDataSource() : ProductDataSource {
    override fun getProducts() : Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteProducts() : Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorite() : Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorite() : Completable {
        TODO("Not yet implemented")
    }
}