package com.arp.mynikestore.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.arp.mynikestore.data.Banner

class BannerSliderAdapter(fragment : Fragment , private val bannerList : List<Banner>) : FragmentStateAdapter(fragment) {

    override fun getItemCount() : Int = bannerList.size

    override fun createFragment(position : Int) : Fragment =
        BannerFragment.newInstance(bannerList[position])

}