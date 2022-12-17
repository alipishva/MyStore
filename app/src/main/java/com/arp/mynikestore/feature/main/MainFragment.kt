package com.arp.mynikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arp.mynikestore.NikeFragment
import com.arp.mynikestore.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
    private val mainViewModel : MainViewModel by viewModel()

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)

        mainViewModel.productLiveData.observe(viewLifecycleOwner){ it ->
            it.forEach { 
                println(it.title)
            }
            Timber.i(it.toString())
        }

        mainViewModel.progressBraLiveData.observe(viewLifecycleOwner){
            setProgressIndicator(it)

        }
    }
}