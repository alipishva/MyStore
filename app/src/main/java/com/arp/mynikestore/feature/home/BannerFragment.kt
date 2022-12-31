package com.arp.mynikestore.feature.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.data.Banner
import com.arp.mynikestore.services.ImageLoadingService
import com.arp.mynikestore.view.NikeImageView
import org.koin.android.ext.android.inject

class BannerFragment : Fragment() {

    private val imageLoadingService : ImageLoadingService by inject()



    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View {

        val imageView = inflater.inflate(R.layout.fragment_banner , container , false) as NikeImageView

        val banner = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(EXTRA_KEY_DATA , Banner::class.java)
                ?: throw IllegalStateException("Banner can not be null")
        } else {
            requireArguments().getParcelable(EXTRA_KEY_DATA)
                ?: throw IllegalStateException("Banner can not be null")
        }

        imageLoadingService.load(imageView , banner.image)

        return imageView
    }

    companion object {
        fun newInstance(banner : Banner) : BannerFragment {
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA , banner)
                }
            }
        }
    }


}