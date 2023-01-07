package com.arp.mynikestore.data.repo.order

import com.arp.mynikestore.data.Checkout
import com.arp.mynikestore.data.SubmitOrderResult
import io.reactivex.rxjava3.core.Single

interface OrderDataSource {
    fun submit(firstName : String , lastName : String , postalCode : String , phoneNumber : String , address : String , paymentMethod : String) : Single<SubmitOrderResult>
    fun checkout(orderId : Int) : Single<Checkout>
}