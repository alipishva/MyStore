package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.MessageResponse
import com.arp.mynikestore.data.TokenResponce
import com.arp.mynikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single

const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"
const val CLIENT_ID = 2

class UserRemoteDataSource(val apiService : ApiService) : UserDataSource {
    override fun login(username : String , password : String) : Single<TokenResponce> {
        return apiService.login(JsonObject().apply {
            addProperty("grant_type" , "password")
            addProperty("username" , username)
            addProperty("password" , password)
            addProperty("client_id" , CLIENT_ID)
            addProperty("client_secret" , CLIENT_SECRET)
        })
    }

    override fun signUp(username : String , password : String) : Single<MessageResponse> {
        return apiService.signUp(JsonObject().apply {
            addProperty("email" , username)
            addProperty("password" , password)
        })
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun SaveToken(token : String , refresh_token : String) {
        TODO("Not yet implemented")
    }
}