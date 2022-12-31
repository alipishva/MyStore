package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.AddToCartResponse
import com.arp.mynikestore.data.CartItemCount
import com.arp.mynikestore.data.CartResponse
import com.arp.mynikestore.data.MessageResponse
import com.arp.mynikestore.data.repo.source.CartDataSource
import io.reactivex.rxjava3.core.Single

class CartRepositoryImpl(val cartRemoteDataSource : CartDataSource) : CartRepository {

    override fun addToCart(productId : Int) : Single<AddToCartResponse> = cartRemoteDataSource.addToCart(productId)


    override fun get() : Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId : Int) : Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId : Int , count : Int) : Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount() : Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}