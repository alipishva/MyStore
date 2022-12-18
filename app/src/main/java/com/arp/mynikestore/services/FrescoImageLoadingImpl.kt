package com.arp.mynikestore.services

import com.arp.mynikestore.view.NikeImageView
import com.facebook.drawee.view.SimpleDraweeView

class FrescoImageLoadingImpl : ImageLoadingService {
    override fun load(imageView : NikeImageView , imageUrl : String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw java.lang.IllegalStateException("imageView must be instance of SimpleDraweeView")
    }
}