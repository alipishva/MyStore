package com.arp.mynikestore

import android.app.Application
import com.arp.mynikestore.data.repo.BannerRepository
import com.arp.mynikestore.data.repo.BannerRepositoryImpl
import com.arp.mynikestore.data.repo.ProductRepository
import com.arp.mynikestore.data.repo.ProductRepositoryImpl
import com.arp.mynikestore.data.repo.source.BannerRemoteDataSource
import com.arp.mynikestore.data.repo.source.ProductLocalDataSource
import com.arp.mynikestore.data.repo.source.ProductRemoteDataSource
import com.arp.mynikestore.feature.main.MainViewModel
import com.arp.mynikestore.feature.main.ProductListAdapter
import com.arp.mynikestore.services.FrescoImageLoadingImpl
import com.arp.mynikestore.services.ImageLoadingService
import com.arp.mynikestore.services.http.ApiService
import com.arp.mynikestore.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class APP : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModule = module {
            single<ApiService> { createApiServiceInstance() }

            single<ImageLoadingService> { FrescoImageLoadingImpl() }

            factory<ProductRepository> {
                ProductRepositoryImpl(ProductRemoteDataSource(get()) , ProductLocalDataSource())
            }

            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }

            factory { ProductListAdapter(get()) }
            viewModel { MainViewModel(get() , get()) }
        }

        startKoin {
            androidContext(this@APP)
            modules(myModule)
        }

    }

}