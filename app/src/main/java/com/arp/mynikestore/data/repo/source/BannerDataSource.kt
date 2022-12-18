package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.Banner
import io.reactivex.rxjava3.core.Single

interface BannerDataSource {
    fun getBannerList(): Single<List<Banner>>

}