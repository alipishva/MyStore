package com.arp.mynikestore.feature.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arp.mynikestore.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container , LoginFragment())
        }.commit()
    }
}