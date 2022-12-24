package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.Product
import com.arp.mynikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRemoteDataSource(private val apiService : ApiService) : ProductDataSource {

    override fun getProducts(sort:Int) : Single<List<Product>> = apiService.getProducts(sort.toString())

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