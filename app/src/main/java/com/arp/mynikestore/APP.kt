package com.arp.mynikestore

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.arp.mynikestore.data.repo.*
import com.arp.mynikestore.data.repo.order.OrderRemoteDataSource
import com.arp.mynikestore.data.repo.order.OrderRepository
import com.arp.mynikestore.data.repo.order.OrderRepositoryImpl
import com.arp.mynikestore.data.repo.source.*
import com.arp.mynikestore.feature.auth.AuthViewModel
import com.arp.mynikestore.feature.cart.CartViewModel
import com.arp.mynikestore.feature.checkout.CheckOutViewModel
import com.arp.mynikestore.feature.common.ProductListAdapter
import com.arp.mynikestore.feature.home.HomeViewModel
import com.arp.mynikestore.feature.list.ProductListViewModel
import com.arp.mynikestore.feature.main.MainViewModel
import com.arp.mynikestore.feature.product.ProductDetailViewModel
import com.arp.mynikestore.feature.product.comment.CommentListViewModel
import com.arp.mynikestore.feature.shipping.ShippingViewModel
import com.arp.mynikestore.services.FrescoImageLoadingImpl
import com.arp.mynikestore.services.ImageLoadingService
import com.arp.mynikestore.services.http.ApiService
import com.arp.mynikestore.services.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
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

            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            single<SharedPreferences> { this@APP.getSharedPreferences("app_setting" , MODE_PRIVATE) }
            single<UserRepository> { UserRepositoryImpl(UserRemoteDataSource(get()) , UserLocalDataSource(get())) }

            single { UserLocalDataSource(get()) }

            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }

            factory { (viewType : Int) -> ProductListAdapter(viewType , get()) }

            viewModel { HomeViewModel(get() , get()) }
            viewModel { (bundle : Bundle) -> ProductDetailViewModel(bundle , get() , get()) }
            viewModel { (productId : Int) -> CommentListViewModel(productId , get()) }
            viewModel { (sort : Int) -> ProductListViewModel(sort , get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId : Int) -> CheckOutViewModel(orderId , get()) }

        }

        startKoin {
            androidContext(this@APP)
            modules(myModule)
        }

        val userRepository : UserRepository = get()
        userRepository.loadToken()

    }

}