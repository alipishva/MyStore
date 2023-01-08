package com.arp.mynikestore.feature.favorites

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.R
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.services.ImageLoadingService
import com.arp.mynikestore.view.NikeImageView

class FavoritesProductAdapter(val favoritesProductEventListener : FavoritesProductEventListener,var products : MutableList<Product>,val imageLoadingService : ImageLoadingService) : RecyclerView.Adapter<FavoritesProductAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : FavoritesViewHolder {
        return FavoritesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_product,parent,false))
    }

    override fun onBindViewHolder(holder : FavoritesViewHolder , position : Int) {
        holder.bind(products[position])

    }

    override fun getItemCount() : Int = products.size

    inner class FavoritesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.findViewById<TextView>(R.id.productTitleTv)
        private val ivProductImage = itemView.findViewById<NikeImageView>(R.id.nikeImageView)

        fun bind(product : Product) {
            tvTitle.text = product.title
            imageLoadingService.load(ivProductImage , product.image)
            itemView.setOnClickListener {
                favoritesProductEventListener.onClick(product)
            }
            itemView.setOnLongClickListener {
                products.remove(product)
                notifyItemRemoved(adapterPosition)
                favoritesProductEventListener.onLongClick(product)
                return@setOnLongClickListener false
            }
        }

    }

    interface FavoritesProductEventListener{
        fun onClick(product : Product)
        fun onLongClick(product : Product)
    }
}