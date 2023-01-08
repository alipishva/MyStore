package com.arp.mynikestore.feature.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.OrderHistoryItem
import com.arp.mynikestore.data.repo.order.OrderRepository

class OrderHistoryViewModel(orderRepository : OrderRepository) : NikeViewModel() {

    private val _orderHistoryLiveData = MutableLiveData<List<OrderHistoryItem>>()
    val orderHistoryLiveData : LiveData<List<OrderHistoryItem>>
        get() = _orderHistoryLiveData


    init {

        progressBarLiveData.value = true
        orderRepository.orderList().asyncNetworkRequest().doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<OrderHistoryItem>>(compositeDisposable) {
                override fun onSuccess(t : List<OrderHistoryItem>) {
                    _orderHistoryLiveData.value = t
                }
            })


    }


}