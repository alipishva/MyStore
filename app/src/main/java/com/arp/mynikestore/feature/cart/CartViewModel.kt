package com.arp.mynikestore.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.R
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.*
import com.arp.mynikestore.data.repo.cart.CartRepository
import io.reactivex.rxjava3.core.Completable
import org.greenrobot.eventbus.EventBus

class CartViewModel(val cartRepository : CartRepository) : NikeViewModel() {

    private val _cartItemLiveData = MutableLiveData<List<CartItem>>()
    val cartItemLiveData : LiveData<List<CartItem>>
        get() = _cartItemLiveData

    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val purchaseDetailLiveData : LiveData<PurchaseDetail>
        get() = _purchaseDetailLiveData

    val emptySateLiveData = MutableLiveData<EmptyState>()

    private fun getCartItems() {
        if (! TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
//            disappear view_cart_empty_state
            emptySateLiveData.value = EmptyState(false)
            cartRepository.get().asyncNetworkRequest().doFinally { progressBarLiveData.value = false }.subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                override fun onSuccess(t : CartResponse) {
                    if (t.cart_items.isNotEmpty()) {
                        _cartItemLiveData.value = t.cart_items
                        _purchaseDetailLiveData.value = PurchaseDetail(t.payable_price , t.shipping_cost , t.total_price)
                    } else
//                            show empty state user did login but did not add any product to cart (add product message show on here)
                        emptySateLiveData.value = EmptyState(true , R.string.cart_empty_state)
                }
            })
        } else
//            show empty state to force user for login
            emptySateLiveData.value = EmptyState(true , R.string.cartEmptyStateLoginRequired , true)
    }

    fun removeItemFromCart(cartItem : CartItem) : Completable {
        return cartRepository.remove(cartItem.cart_item_id).doAfterSuccess {
            calculateAndPublishPurchaseDetail()
//                update cart badge on remove item
            val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCount?.let {
                it.count -= cartItem.count
                EventBus.getDefault().postSticky(it)
            }
            _cartItemLiveData.value?.let {
                if (it.isEmpty())
//                        after remove check if cart list is empty show (empty state add product) in here we know user is login
                    emptySateLiveData.postValue(EmptyState(true , R.string.cart_empty_state))
            }
        }.ignoreElement()
    }

    fun increaseCartItemCount(cartItem : CartItem) : Completable = cartRepository.changeCount(cartItem.cart_item_id , ++ cartItem.count)
        .doAfterSuccess {
            calculateAndPublishPurchaseDetail()
//                increase(1) product count
            val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCount?.let {
                it.count += 1
                EventBus.getDefault().postSticky(it)
            }
        }.ignoreElement()

    fun decreaseCartItemCount(cartItem : CartItem) : Completable = cartRepository.changeCount(cartItem.cart_item_id , -- cartItem.count).doAfterSuccess {
            calculateAndPublishPurchaseDetail()
//                decrease(1) product count
            val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCount?.let {
                it.count -= 1
                EventBus.getDefault().postSticky(it)
            }
        }.ignoreElement()

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