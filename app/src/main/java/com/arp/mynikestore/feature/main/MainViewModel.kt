package com.arp.mynikestore.feature.main

import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.data.CartItemCount
import com.arp.mynikestore.data.TokenContainer
import com.arp.mynikestore.data.repo.cart.CartRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository : CartRepository) : NikeViewModel() {

    fun getCartItemCount() {
//        if user login get cart item count
//        we dont need (observeON) we dont care about result to be on main thread
//        and then send result to mainActivity with EventBus stickyEvent
        if (! TokenContainer.token.isNullOrEmpty()) {
            cartRepository.getCartItemsCount().subscribeOn(Schedulers.io()).subscribe(object : NikeSingleObserver<CartItemCount>(compositeDisposable) {
                    override fun onSuccess(t : CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }


}