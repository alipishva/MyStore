package com.arp.mynikestore.feature.shipping

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.data.PurchaseDetail
import com.arp.mynikestore.feature.cart.CartItemAdapter
import kotlinx.android.synthetic.main.activity_shipping.*

class ShippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_KEY_DATA , PurchaseDetail::class.java) ?: throw IllegalStateException("Purchase detail cannot be null")
        } else {
            intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA) ?: throw IllegalStateException("Purchase detail cannot be null")
        }

        val viewHolder = CartItemAdapter.PurchaseDetailViewHHolder(purchaseDetailView)
        val discount = purchaseDetail.total_price - purchaseDetail.payable_price
        viewHolder.bind(purchaseDetail.total_price , purchaseDetail.shipping_cost , purchaseDetail.payable_price , discount)
    }
}