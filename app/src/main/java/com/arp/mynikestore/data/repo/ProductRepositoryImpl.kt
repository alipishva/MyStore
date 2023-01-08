package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.source.ProductDataSource
import com.arp.mynikestore.data.repo.source.ProductLocalDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ProductRepositoryImpl(private val remoteDataSource : ProductDataSource , val localDataSource : ProductLocalDataSource) : ProductRepository {

    override fun getProducts(sort : Int) : Single<List<Product>> {
       return localDataSource.getFavoriteProducts().flatMap { favoriteProducts ->
            remoteDataSource.getProducts(sort).doOnSuccess {
                val favProductIds = favoriteProducts.map {
                    it.id
                }
                it.forEach { product ->
                    if (favProductIds.contains(product.id)) product.isFavorite = true
                }
            }
        }
    }

    override fun getFavoriteProducts() : Single<List<Product>> {
        return localDataSource.getFavoriteProducts()
    }

    override fun addToFavorite(Product : Product) : Completable {
        return localDataSource.addToFavorite(Product)
    }

    override fun deleteFromFavorite(Product : Product) : Completable {
        return localDataSource.deleteFromFavorite(Product)
    }
}