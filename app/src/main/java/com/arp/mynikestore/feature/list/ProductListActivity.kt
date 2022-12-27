package com.arp.mynikestore.feature.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R

class ProductListActivity : NikeActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
    }
}