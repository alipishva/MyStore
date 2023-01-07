package com.arp.mynikestore.feature.shipping

import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.data.SubmitOrderResult
import com.arp.mynikestore.data.repo.order.OrderRepository
import io.reactivex.rxjava3.core.Single

const val PAYMENT_METHOD_COD="cash_on_delivery"
const val PAYMENT_METHOD_ONLINE="online"

class ShippingViewModel(private val orderRepository : OrderRepository) : NikeViewModel(){

    fun submitOrder(firstName : String , lastName : String , postalCode : String , phoneNumber : String , address : String , paymentMethod : String):Single<SubmitOrderResult>{
        return orderRepository.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)

    }
}