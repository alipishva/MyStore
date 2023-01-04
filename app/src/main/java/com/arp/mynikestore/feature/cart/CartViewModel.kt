package com.arp.mynikestore.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.CartItem
import com.arp.mynikestore.data.CartResponse
import com.arp.mynikestore.data.PurchaseDetail
import com.arp.mynikestore.data.TokenContainer
import com.arp.mynikestore.data.repo.CartRepository
import io.reactivex.rxjava3.core.Completable

class CartViewModel(val cartRepository : CartRepository) : NikeViewModel() {

    private val _cartItemLiveData = MutableLiveData<List<CartItem>>()
    val cartItemLiveData : LiveData<List<CartItem>>
        get() = _cartItemLiveData

    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val purchaseDetailLiveData : LiveData<PurchaseDetail>
        get() = _purchaseDetailLiveData

    private fun getCartItems() {
        if (! TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            cartRepository.get().asyncNetworkRequest().doFinally { progressBarLiveData.value = false }.subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t : CartResponse) {
                        if (t.cart_items.isNotEmpty()) {
                            _cartItemLiveData.value = t.cart_items
                            _purchaseDetailLiveData.value = PurchaseDetail(t.payable_price , t.shipping_cost , t.total_price)
                        }
                    }
                })
        }
    }

    fun removeItemFromCart(cartItem : CartItem) : Completable = cartRepository.remove(cartItem.cart_item_id).doAfterSuccess { calculateAndPublishPurchaseDetail() }.ignoreElement()

    fun increaseCartItemCount(cartItem : CartItem) : Completable =
        cartRepository.changeCount(cartItem.cart_item_id , ++ cartItem.count).doAfterSuccess { calculateAndPublishPurchaseDetail() }.ignoreElement()

    fun decreaseCartItemCount(cartItem : CartItem) : Completable =
        cartRepository.changeCount(cartItem.cart_item_id , -- cartItem.count).doAfterSuccess { calculateAndPublishPurchaseDetail() }.ignoreElement()

    fun refresh() {
        getCartItems()
    }

    private fun calculateAndPublishPurchaseDetail() {
        _cartItemLiveData.value?.let { cartItems ->
            _purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                var discount = 0
                cartItems.forEach {
                    totalPrice = it.product.price * it.count
                    payablePrice = (it.product.price - it.product.discount) * it.count
                }
                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice

                _purchaseDetailLiveData.postValue(purchaseDetail)
            }

        }
    }
}