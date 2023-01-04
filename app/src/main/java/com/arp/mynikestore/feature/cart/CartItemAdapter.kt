package com.arp.mynikestore.feature.cart

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.R
import com.arp.mynikestore.common.formatPrice
import com.arp.mynikestore.data.CartItem
import com.arp.mynikestore.data.PurchaseDetail
import com.arp.mynikestore.services.ImageLoadingService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_purchase_details.view.*
import timber.log.Timber

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAIL = 1

class CartItemAdapter(val cartItems : MutableList<CartItem> , val imageLoadingService : ImageLoadingService , val cartItemViewCallBacks : CartItemViewCallBacks) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail : PurchaseDetail? = null
    var discount = 0

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM) {
            CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart , parent , false))
        } else {
            PurchaseDetailViewHHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_details , parent , false))
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder , position : Int) {

        if (holder is CartItemViewHolder) {
            holder.bindCartItem(cartItems[position])
        } else if (holder is PurchaseDetailViewHHolder) {
            cartItems.forEach {
                discount += it.product.discount
            }
            purchaseDetail?.let {
                holder.bind(it.total_price , it.shipping_cost , it.payable_price , discount)
            }

        }
    }

    override fun getItemCount() : Int = cartItems.size + 1

    override fun getItemViewType(position : Int) : Int {
        if (position == cartItems.size) {
            return VIEW_TYPE_PURCHASE_DETAIL
        } else {
            return VIEW_TYPE_CART_ITEM
        }
    }

    fun removeCartItem(cartItem : CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > - 1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun increaseCount(cartItem : CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > - 1) {
            cartItems[index].changeCountProgressBarVisibility = false
            notifyItemChanged(index)
        }
    }

    fun decreaseCount(cartItem : CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > - 1) {
            cartItems[index].changeCountProgressBarVisibility = false
            notifyItemChanged(index)
        }
    }



    inner class CartItemViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView) , LayoutContainer {


        fun bindCartItem(cartItem : CartItem) {

            containerView.tv_cart_item_product_title.text = cartItem.product.title
            containerView.tv_cart_Item_count.text = cartItem.count.toString()

            containerView.tv_cart_item_previous_price.apply {
                Timber.i("previous price ${cartItem.product.previous_price}")
                text = formatPrice(cartItem.product.price + cartItem.product.discount)
                paintFlags = paintFlags or STRIKE_THRU_TEXT_FLAG
            }

            containerView.tv_cart_item_price.apply {
                text = formatPrice(cartItem.product.price)
            }



            imageLoadingService.load(containerView.iv_cart_item_product , cartItem.product.image)

            containerView.btn_cart_item_remove_from_cart.setOnClickListener {
                cartItemViewCallBacks.onRemoveCartItemClick(cartItem)
            }

            containerView.changeCountProgressBar.visibility = if (cartItem.changeCountProgressBarVisibility) View.VISIBLE else View.GONE

            containerView.tv_cart_Item_count.visibility=if (cartItem.changeCountProgressBarVisibility) View.INVISIBLE else View.VISIBLE

            containerView.btn_cart_item_increase.setOnClickListener {
                cartItem.changeCountProgressBarVisibility = true
                containerView.changeCountProgressBar.visibility = View.VISIBLE
                containerView.tv_cart_Item_count.visibility = View.INVISIBLE
                cartItemViewCallBacks.onIncreaseCartItemClick(cartItem)
            }

            containerView.btn_cart_item_decrease.setOnClickListener {
                if (cartItem.count > 1) {
                    cartItem.changeCountProgressBarVisibility = true
                    containerView.changeCountProgressBar.visibility = View.VISIBLE
                    containerView.tv_cart_Item_count.visibility = View.INVISIBLE
                    cartItemViewCallBacks.onDecreaseCartItemClick(cartItem)
                }
            }

            containerView.iv_cart_item_product.setOnClickListener {
                cartItemViewCallBacks.onProductImageClick(cartItem)
            }
        }

    }

    class PurchaseDetailViewHHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView) , LayoutContainer {
        fun bind(totalPrice : Int , shippingCost : Int , payablePrice : Int , discount : Int) {
            containerView.tv_total_price.text = formatPrice(totalPrice)
            containerView.tv_shipping_cost.text = formatPrice(shippingCost)
            containerView.tv_payable_price.text = formatPrice(payablePrice)
            containerView.tv_cart_item_discount.text = formatPrice(discount)
        }
    }

    interface CartItemViewCallBacks {
        fun onRemoveCartItemClick(cartItem : CartItem)
        fun onIncreaseCartItemClick(cartItem : CartItem)
        fun onDecreaseCartItemClick(cartItem : CartItem)
        fun onProductImageClick(cartItem : CartItem)

    }


}