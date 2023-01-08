package com.arp.mynikestore.data.repo.cart

import com.arp.mynikestore.data.AddToCartResponse
import com.arp.mynikestore.data.CartItemCount
import com.arp.mynikestore.data.CartResponse
import com.arp.mynikestore.data.MessageResponse
import io.reactivex.rxjava3.core.Single

class CartRepositoryImpl(private val cartRemoteDataSource : CartDataSource) : CartRepository {

    override fun addToCart(productId : Int) : Single<AddToCartResponse> = cartRemoteDataSource.addToCart(productId)


    override fun get() : Single<CartResponse> = cartRemoteDataSource.get()

    override fun remove(cartItemId : Int) : Single<MessageResponse> = cartRemoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId : Int , count : Int) : Single<AddToCartResponse> = cartRemoteDataSource.changeCount(cartItemId , count)

    override fun getCartItemsCount() : Single<CartItemCount> = cartRemoteDataSource.getCartItemsCount()
}