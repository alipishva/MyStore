package com.arp.mynikestore.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.Checkout
import com.arp.mynikestore.data.repo.order.OrderRepository

class CheckOutViewModel(orderId : Int , orderRepository : OrderRepository) : NikeViewModel() {

    val checkoutLiveData = MutableLiveData<Checkout>()

    init {
        orderRepository.checkout(orderId).asyncNetworkRequest().subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
            override fun onSuccess(t : Checkout) {
                checkoutLiveData.value = t
            }
        })
    }
}