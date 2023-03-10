package com.arp.mynikestore.data.repo.user

import android.content.SharedPreferences
import com.arp.mynikestore.data.MessageResponse
import com.arp.mynikestore.data.TokenContainer
import com.arp.mynikestore.data.TokenResponce
import com.arp.mynikestore.data.repo.user.UserDataSource
import io.reactivex.rxjava3.core.Single

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"

class UserLocalDataSource(private val sharedPreferences : SharedPreferences) : UserDataSource {
    override fun login(username : String , password : String) : Single<TokenResponce> {
        TODO("Not yet implemented")
    }

    override fun signUp(username : String , password : String) : Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(sharedPreferences.getString(ACCESS_TOKEN , null) , sharedPreferences.getString(REFRESH_TOKEN , null))
    }

    override fun saveToken(token : String , refresh_token : String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN , token)
            putString(REFRESH_TOKEN , refresh_token)
        }.apply()
    }

    override fun saveUserName(username : String) {
        sharedPreferences.edit().apply {
            putString("username" , username)
        }.apply()
    }

    override fun getUserName() : String = sharedPreferences.getString("username" , "") ?: ""

    override fun signOut() {
        sharedPreferences.edit().apply {
            clear()
        }.apply()
    }
}