package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.Banner
import io.reactivex.rxjava3.core.Single

interface BannerRepository {

    fun getBannerList():Single<List<Banner>>

}