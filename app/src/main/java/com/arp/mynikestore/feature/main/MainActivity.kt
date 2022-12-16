package com.arp.mynikestore.feature.main

import android.os.Bundle
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R

class MainActivity : NikeActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}