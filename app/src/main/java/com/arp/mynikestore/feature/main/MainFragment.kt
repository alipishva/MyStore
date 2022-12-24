package com.arp.mynikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R
import com.arp.mynikestore.data.Product
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {

    private val mainViewModel : MainViewModel by viewModel()
    val productListAdapter : ProductListAdapter by inject()

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        rv_latest_product.layoutManager =
            LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        rv_latest_product.adapter = productListAdapter

        rv_popular_product.layoutManager =
            LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
        rv_popular_product.adapter = productListAdapter

        mainViewModel.productPopularLiveData.observe(viewLifecycleOwner) {
            productListAdapter.products = it as ArrayList<Product>

        }

        mainViewModel.productLatestLiveData.observe(viewLifecycleOwner) { it ->
            productListAdapter.products = ArrayList<Product>()
            productListAdapter.products = it as ArrayList<Product>
            Timber.i(it.toString())
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