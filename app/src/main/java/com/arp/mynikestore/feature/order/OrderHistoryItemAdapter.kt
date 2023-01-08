package com.arp.mynikestore.feature.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.R
import com.arp.mynikestore.common.convertDpToPixel
import com.arp.mynikestore.common.formatPrice
import com.arp.mynikestore.view.NikeImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order_history.view.*

class OrderHistoryItemAdapter(val context : Context , val orderHistoryItems:List<com.arp.mynikestore.data.OrderHistoryItem>) : RecyclerView.Adapter<OrderHistoryItemAdapter.OrderHistoryViewHolder>() {

    private val layoutParams : LinearLayout.LayoutParams

    init {
        val size = convertDpToPixel(100f , context).toInt()
        val margin = convertDpToPixel(8f , context).toInt()
        layoutParams = LinearLayout.LayoutParams(size , size)
        layoutParams.setMargins(margin , 0 , margin , 0)
    }

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : OrderHistoryViewHolder {
        return OrderHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history,parent,false))
    }

    override fun onBindViewHolder(holder : OrderHistoryViewHolder , position : Int) =holder.bind(orderHistoryItems[position])

    override fun getItemCount() : Int = orderHistoryItems.size

    inner class OrderHistoryViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView) , LayoutContainer {

        fun bind(orderHistoryItem : com.arp.mynikestore.data.OrderHistoryItem) {
            containerView.tv_order_history_id.text = orderHistoryItem.id.toString()
            containerView.tv_order_history_amount.text = formatPrice(orderHistoryItem.payable)
            containerView.ll_order_history_products.removeAllViews()
            orderHistoryItem.order_items.forEach {
                val imageView = NikeImageView(context)
                imageView.layoutParams = layoutParams
                imageView.setImageURI(it.product.image)
                containerView.ll_order_history_products.addView(imageView)
            }

        }


    }

}