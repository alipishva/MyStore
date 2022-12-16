package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.source.ProductDataSource
import com.arp.mynikestore.data.repo.source.ProductLocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRepositoryImpl(
    private val remoteDataSource : ProductDataSource ,
    val localDataSource : ProductLocalDataSource
) : ProductRepository {

    override fun getProducts() : Single<List<Product>> =remoteDataSource.getProducts()
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