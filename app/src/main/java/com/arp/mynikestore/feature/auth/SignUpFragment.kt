package com.arp.mynikestore.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R

class SignUpFragment: NikeFragment() {

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {

        return inflater.inflate(R.layout.fragment_signup , container , false)
    }}