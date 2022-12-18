package com.arp.mynikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R
import com.arp.mynikestore.common.convertDpToPixel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
    private val mainViewModel : MainViewModel by viewModel()

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        mainViewModel.productLiveData.observe(viewLifecycleOwner) { it ->

            Timber.i(it.toString())
        }

        mainViewModel.progressBraLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)

        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) { it ->
            val bannerSliderAdapter = BannerSliderAdapter(this , it)
            banner_slider_view_pager.adapter = bannerSliderAdapter

//            val viewpagerHeight = (((banner_slider_view_pager.measuredWidth - convertDpToPixel(32f ,
//                requireContext())) * 173) / 328).toInt()
//
//            val layoutParams = banner_slider_view_pager.layoutParams
//
//            layoutParams.height = viewpagerHeight

//            Timber.i("viewpagerHeight ---> $viewpagerHeight")
            slider_indicator.attachTo(banner_slider_view_pager)

        }
    }
}