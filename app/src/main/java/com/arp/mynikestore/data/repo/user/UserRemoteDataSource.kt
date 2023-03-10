package com.arp.mynikestore.data.repo.user

import com.arp.mynikestore.data.MessageResponse
import com.arp.mynikestore.data.TokenResponce
import com.arp.mynikestore.data.repo.user.UserDataSource
import com.arp.mynikestore.services.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single

const val CLIENT_ID = 2
const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

class UserRemoteDataSource(private val apiService : ApiService) : UserDataSource {
    override fun login(username : String , password : String) : Single<TokenResponce> {
        return apiService.login(JsonObject().apply {
            addProperty("username" , username)
            addProperty("password" , password)
            addProperty("grant_type" , "password")
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

    override fun saveToken(token : String , refresh_token : String) {
        TODO("Not yet implemented")
    }

    override fun saveUserName(username : String) {
        TODO("Not yet implemented")
    }

    override fun getUserName() : String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}