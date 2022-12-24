package com.arp.mynikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : NikeFragment() {

    private val mainViewModel : MainViewModel by viewModel()
    private val productListAdapter : ProductListAdapter by inject()

    override fun onCreateView(
        inflater : LayoutInflater ,
        container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View? {

        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)


        mainViewModel.productLatestLiveData.observe(viewLifecycleOwner) { it ->
            productListAdapter.setData(it)
            rv_latest_product.apply {
                layoutManager =
                    LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
                adapter = productListAdapter
            }
        }

        mainViewModel.productPopularLiveData.observe(viewLifecycleOwner) {
            productListAdapter.setData(it)
            rv_popular_product.apply {
                layoutManager =
                    LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
                adapter = productListAdapter
            }
        }




        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)

        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) { it ->
            //setup slider adapter and send it for viewPager
            val bannerSliderAdapter = BannerSliderAdapter(this , it)
            banner_slider_view_pager.adapter = bannerSliderAdapter

            //attach indicator to viewPager
            slider_indicator.attachTo(banner_slider_view_pager)

        }
    }
}