package com.arp.mynikestore.services

import com.arp.mynikestore.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView : NikeImageView , imageUrl : String)
}