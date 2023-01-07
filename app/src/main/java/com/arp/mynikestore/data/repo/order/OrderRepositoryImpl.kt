package com.arp.mynikestore.data.repo.order

import com.arp.mynikestore.data.Checkout
import com.arp.mynikestore.data.SubmitOrderResult
import io.reactivex.rxjava3.core.Single

class OrderRepositoryImpl(val orderDataSource : OrderDataSource) : OrderRepository {
    override fun submit(firstName : String , lastName : String , postalCode : String , phoneNumber : String , address : String , paymentMethod : String) : Single<SubmitOrderResult> {
        return orderDataSource.submit(firstName , lastName , postalCode , phoneNumber , address , paymentMethod)
    }

    override fun checkout(orderId : Int) : Single<Checkout> {
        return orderDataSource.checkout(orderId)
    }

}