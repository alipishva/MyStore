package com.arp.mynikestore.feature.favorites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.feature.product.ProductDetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorites_product.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class FavoritesProductActivity : NikeActivity() , FavoritesProductAdapter.FavoritesProductEventListener {

    val viewModel : FavoritesProductViewModel by inject()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites_product)

        helpBtn.setOnClickListener {
            showSnackBar(getString(R.string.fav_help_message),Snackbar.LENGTH_LONG)
        }


        viewModel.favoritesProductLiveData.observe(this) {
            if (it.isNotEmpty()) {
                favoriteProductsRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)
                favoriteProductsRv.adapter = FavoritesProductAdapter(this , it as MutableList<Product> , get())
            }else{
                showEmptySate(R.layout.view_default_empty_state)
                emptyStateMessageTv.text=getString(R.string.fav_empty_state_message)
            }
        }

    }

    override fun onClick(product : Product) {
        startActivity(Intent(this , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }

    override fun onLongClick(product : Product) {
        viewModel.removeProductFromFavorites(product)
    }
}