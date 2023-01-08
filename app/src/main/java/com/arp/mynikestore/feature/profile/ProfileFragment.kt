package com.arp.mynikestore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R
import com.arp.mynikestore.feature.auth.AuthActivity
import com.arp.mynikestore.feature.favorites.FavoritesProductActivity
import com.arp.mynikestore.feature.order.OrderHistoryActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : NikeFragment() {

    val viewModel : ProfileViewModel by viewModel()

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_profile , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        btn_fav_product.setOnClickListener {
            startActivity(Intent(requireContext() , FavoritesProductActivity::class.java))
        }

        btn_order_history.setOnClickListener {
            startActivity(Intent(requireContext() , OrderHistoryActivity::class.java))
        }


    }


    override fun onResume() {
        super.onResume()
        checkAuthState()
    }


    private fun checkAuthState() {
        if (viewModel.isSignedIn) {
            authBtn.text = getString(R.string.signOut)
            authBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_logout_24 , 0 , 0 , 0)
            tv_user_name.text = viewModel.userName
            authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
        } else {
            authBtn.text = getString(R.string.SignIn)
            authBtn.setOnClickListener {
                startActivity(Intent(requireContext() , AuthActivity::class.java))
            }
            authBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_sign_in , 0 , 0 , 0)
            tv_user_name.text = getString(R.string.guest_user)
        }
    }
}