package com.arp.mynikestore.data.repo.product

import androidx.room.*
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.product.ProductDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ProductLocalDataSource: ProductDataSource {
    override fun getProducts(sort:Int) : Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoriteProducts() : Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavorite(Product:Product) : Completable

    @Delete
    override fun deleteFromFavorite(Product:Product) : Completable
}