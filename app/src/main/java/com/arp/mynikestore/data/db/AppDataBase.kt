package com.arp.mynikestore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class] , version = 1 , exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun productDao() : ProductLocalDataSource
}