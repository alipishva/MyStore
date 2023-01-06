package com.arp.mynikestore.services.http

import com.arp.mynikestore.data.*
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort : String) : Single<List<Product>>

    @GET("banner/slider")
    fun getBannerList() : Single<List<Banner>>

    @GET("comment/list")
    fun getCommentList(@Query("product_id") productId : Int) : Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject : JsonObject) : Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeItemFromCart(@Body jsonObject : JsonObject) : Single<MessageResponse>

    @GET("cart/list")
    fun getCart() : Single<CartResponse>

    @POST("cart/changeCount")
    fun changeCartItemCount(@Body jsonObject : JsonObject) : Single<AddToCartResponse>

    @GET("cart/count")
    fun getCartItemCount() : Single<CartItemCount>

    @POST("auth/token")
    fun login(@Body jsonObject : JsonObject) : Single<TokenResponce>

    @POST("user/register")
    fun signUp(@Body jsonObject : JsonObject) : Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject : JsonObject) : Call<TokenResponce>

    @POST("order/submit")
    fun submitOrder(@Body jsonObject : JsonObject) : Single<SubmitOrderResult>

    @GET("order/checkout")
    fun checkout(@Query("order_id") orderId : Int) : Single<Checkout>
}

fun createApiServiceInstance() : ApiService {

    val okHttpClient = OkHttpClient.Builder().addInterceptor {
        val oldRequest = it.request()
        val newRequestBuilder = oldRequest.newBuilder()
        if (TokenContainer.token != null) newRequestBuilder.addHeader("Authorization" , "Bearer ${TokenContainer.token}")
        newRequestBuilder.addHeader("Accept" , "application/json")
        newRequestBuilder.method(oldRequest.method , oldRequest.body)
        return@addInterceptor it.proceed(newRequestBuilder.build())

    }.addInterceptor(HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }).build()

    val retrofit = Retrofit.Builder().baseUrl("http://expertdevelopers.ir/api/v1/").addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    return retrofit.create(ApiService::class.java)
}