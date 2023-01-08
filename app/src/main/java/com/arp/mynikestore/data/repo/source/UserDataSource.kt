package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.MessageResponse
import com.arp.mynikestore.data.TokenResponce
import io.reactivex.rxjava3.core.Single

interface UserDataSource {
    fun login(username : String , password : String) : Single<TokenResponce>
    fun signUp(username : String , password : String) : Single<MessageResponse>
    fun loadToken()
    fun saveToken(token : String , refresh_token : String)
    fun saveUserName(username : String)
    fun getUserName() : String
    fun signOut()

}