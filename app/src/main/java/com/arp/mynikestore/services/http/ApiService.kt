package com.arp.mynikestore.services.http

import com.arp.mynikestore.data.Banner
import com.arp.mynikestore.data.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort : String) : Single<List<Product>>

    @GET("banner/slider")
    fun getBannerList() : Single<List<Banner>>

}

fun createApiServiceInstance() : ApiService {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

        .build()
    return retrofit.create(ApiService::class.java)
}