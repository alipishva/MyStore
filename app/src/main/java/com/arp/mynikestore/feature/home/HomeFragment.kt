package com.arp.mynikestore.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.SORT_LATEST_PRODUCT
import com.arp.mynikestore.feature.common.ProductListAdapter
import com.arp.mynikestore.feature.common.VIEW_TYPE_ROUND
import com.arp.mynikestore.feature.list.ProductListActivity
import com.arp.mynikestore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : NikeFragment() , ProductListAdapter.OnProductClickListener {

//    inject view model
    private val homeViewModel : HomeViewModel by viewModel()
//    inject ProductListAdapter with passing parameters
    private val productListAdapter : ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ROUND) }

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {

        return inflater.inflate(R.layout.fragment_home , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

//        on item click happen and this fragment implement this interface
        productListAdapter.onProductClickListener = this

//        go to ProductListActivity and by default show LATEST product
        btn_home_product_latest.setOnClickListener {
            startActivity(Intent(requireContext() , ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST_PRODUCT)
            })
        }

//      observe for LATEST product show on recyclerView the result from LiveData
        homeViewModel.productLatestLiveData.observe(viewLifecycleOwner) { it ->
            productListAdapter.setData(it)
            rv_latest_product.apply {
                layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
                adapter = productListAdapter
            }
        }
//      observe for POPULAR product
        homeViewModel.productPopularLiveData.observe(viewLifecycleOwner) {
            productListAdapter.setData(it)
            rv_popular_product.apply {
                layoutManager = LinearLayoutManager(requireContext() , RecyclerView.HORIZONTAL , false)
                adapter = productListAdapter
            }
        }

//        observe for progressBar show/dismiss
        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)

        }

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner) { it ->
            //setup slider adapter and send it for viewPager
            val bannerSliderAdapter = BannerSliderAdapter(this , it)
            banner_slider_view_pager.adapter = bannerSliderAdapter

            //attach indicator to viewPager
            slider_indicator.attachTo(banner_slider_view_pager)

        }
    }

//    on item click happen show ProductDetailActivity with selected product
    override fun onProductClick(product : Product) {
        startActivity(Intent(requireContext() , ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA , product)
        })
    }
}