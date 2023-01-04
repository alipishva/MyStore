package com.arp.mynikestore.feature.common

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.R
import com.arp.mynikestore.common.formatPrice
import com.arp.mynikestore.common.implementSpringAnimationTrait
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.services.ImageLoadingService
import com.arp.mynikestore.view.NikeImageView
import java.util.*

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(var viewType : Int = VIEW_TYPE_ROUND , val imageLoadingService : ImageLoadingService) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var onProductClickListener : OnProductClickListener? = null

    var products = mutableListOf<Product>()

    fun setData(data : List<Product>) {
        products.apply {
            clear()
            addAll(data)
        }
    }

    override fun getItemViewType(position : Int) : Int {
        return viewType
    }

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : ProductViewHolder {
        val layoutResId = when (viewType) {
            VIEW_TYPE_ROUND -> R.layout.item_product
            VIEW_TYPE_SMALL ->R.layout.item_product_small
            VIEW_TYPE_LARGE->R.layout.item_product_large
            else -> throw IllegalStateException("View type is not valid")
        }

        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId , parent , false))
    }

    override fun onBindViewHolder(holder : ProductViewHolder , position : Int) {
        holder.bindProduct(products[position])
    }

    override fun getItemCount() : Int = products.size


    inner class ProductViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val ivProductImage = itemView.findViewById<NikeImageView>(R.id.iv_product_image)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_product_title)
        val tvPreviousPrice = itemView.findViewById<TextView>(R.id.tv_product_previous_price)
        val tvCurrentPrice = itemView.findViewById<TextView>(R.id.tv_product_current_Price)

        fun bindProduct(product : Product) {
            val currency : Currency = Currency.getInstance(Locale.getDefault())
            // store the currency symbol in variable
            val symbol : String = currency.symbol
            imageLoadingService.load(ivProductImage , product.image)
            tvTitle.text = product.title

            tvPreviousPrice.apply {
                text = "$symbol${product.previous_price}"
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            tvCurrentPrice.apply {
                text = formatPrice(product.price)
            }

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                onProductClickListener?.onProductClick(product)
            }
        }
    }

    interface OnProductClickListener {
        fun onProductClick(product : Product)
    }
}