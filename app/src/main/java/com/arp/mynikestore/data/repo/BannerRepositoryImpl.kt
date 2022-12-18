package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.Banner
import com.arp.mynikestore.data.repo.source.BannerDataSource
import io.reactivex.rxjava3.core.Single

class BannerRepositoryImpl(private val bannerRemoteDataSource : BannerDataSource) : BannerRepository {
    override fun getBannerList() : Single<List<Banner>> = bannerRemoteDataSource.getBannerList()
}