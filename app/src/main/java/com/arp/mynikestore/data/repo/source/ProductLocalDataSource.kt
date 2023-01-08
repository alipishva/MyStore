package com.arp.mynikestore.data.repo.source

import androidx.room.*
import com.arp.mynikestore.data.Product
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.DELETE

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