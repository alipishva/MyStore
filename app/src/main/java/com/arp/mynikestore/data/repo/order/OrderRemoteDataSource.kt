package com.arp.mynikestore.data.repo.order

import com.arp.mynikestore.data.Checkout
import com.arp.mynikestore.data.OrderHistoryItem
import com.arp.mynikestore.data.SubmitOrderResult
import com.arp.mynikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single

class OrderRemoteDataSource(val apiService : ApiService) : OrderDataSource {
    override fun submit(firstName : String , lastName : String , postalCode : String , phoneNumber : String , address : String , paymentMethod : String) : Single<SubmitOrderResult> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name" , firstName)
            addProperty("last_name" , lastName)
            addProperty("postal_code" , postalCode)
            addProperty("mobile" , phoneNumber)
            addProperty("address" , address)
            addProperty("payment_method" , paymentMethod)
        })
    }

    override fun checkout(orderId : Int) : Single<Checkout> {
        return apiService.checkout(orderId)
    }

    override fun orderList() : Single<List<OrderHistoryItem>> = apiService.orders()

}