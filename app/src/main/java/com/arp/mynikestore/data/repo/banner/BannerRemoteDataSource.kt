package com.arp.mynikestore.data.repo.banner

import com.arp.mynikestore.data.Banner
import com.arp.mynikestore.data.repo.banner.BannerDataSource
import com.arp.mynikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class BannerRemoteDataSource(private val apiService : ApiService) : BannerDataSource {
    override fun getBannerList() : Single<List<Banner>> = apiService.getBannerList()
}