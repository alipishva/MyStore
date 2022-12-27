package com.arp.mynikestore

import android.app.Application
import android.os.Bundle
import com.arp.mynikestore.data.repo.*
import com.arp.mynikestore.data.repo.source.*
import com.arp.mynikestore.feature.main.MainViewModel
import com.arp.mynikestore.feature.main.ProductListAdapter
import com.arp.mynikestore.feature.product.ProductDetailViewModel
import com.arp.mynikestore.feature.product.comment.CommentListViewModel
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

            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }

            factory { ProductListAdapter(get()) }
            viewModel { MainViewModel(get() , get()) }
            viewModel { (bundle : Bundle) -> ProductDetailViewModel(bundle , get()) }
            viewModel { (productId : Int) -> CommentListViewModel(productId , get()) }
        }

        startKoin {
            androidContext(this@APP)
            modules(myModule)
        }

    }

}