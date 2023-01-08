package com.arp.mynikestore.data.repo.cart

import com.arp.mynikestore.data.AddToCartResponse
import com.arp.mynikestore.data.CartItemCount
import com.arp.mynikestore.data.CartResponse
import com.arp.mynikestore.data.MessageResponse
import com.arp.mynikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single

class CartRemoteDataSource(val apiService : ApiService) : CartDataSource {

    override fun addToCart(productId : Int) : Single<AddToCartResponse> = apiService.addToCart(JsonObject().apply { addProperty("product_id" , productId) })

    override fun get() : Single<CartResponse> = apiService.getCart()

    override fun remove(cartItemId : Int) : Single<MessageResponse> = apiService.removeItemFromCart(JsonObject().apply {
        addProperty("cart_item_id" , cartItemId)
    })

    override fun changeCount(cartItemId : Int , count : Int) : Single<AddToCartResponse> = apiService.changeCartItemCount(JsonObject().apply {
        addProperty("cart_item_id" , cartItemId)
        addProperty("count" , count)

    })

    override fun getCartItemsCount() : Single<CartItemCount> =apiService.getCartItemCount()
}