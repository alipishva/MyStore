package com.arp.mynikestore

import android.app.Application
import com.arp.mynikestore.data.repo.ProductRepository
import com.arp.mynikestore.data.repo.ProductRepositoryImpl
import com.arp.mynikestore.data.repo.source.ProductLocalDataSource
import com.arp.mynikestore.data.repo.source.ProductRemoteDataSource
import com.arp.mynikestore.feature.main.MainViewModel
import com.arp.mynikestore.services.http.ApiService
import com.arp.mynikestore.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class APP : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant()

        val myModule = module {
            single<ApiService> { createApiServiceInstance() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()),
                ProductLocalDataSource()) }
            viewModel { MainViewModel(get()) }
        }

        startKoin{
            androidContext(this@APP)
            modules(myModule)
        }

    }

}