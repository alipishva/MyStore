package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.TokenContainer
import com.arp.mynikestore.data.TokenResponce
import com.arp.mynikestore.data.repo.source.UserDataSource
import io.reactivex.rxjava3.core.Completable

class UserRepositoryImpl(private val userRemoteDataSource : UserDataSource , private val userLocalDataSource : UserDataSource) : UserRepository {

    override fun login(username : String , password : String) : Completable {
        return userRemoteDataSource.login(username , password).doOnSuccess {
            onSuccessFulLogin(username , it)
        }.ignoreElement()
    }

    override fun signUp(username : String , password : String) : Completable {
        return userRemoteDataSource.signUp(username , password).flatMap {
            userRemoteDataSource.login(username , password)
        }.doOnSuccess {
            onSuccessFulLogin(username , it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName() : String = userLocalDataSource.getUserName()

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null , null)
    }

    private fun onSuccessFulLogin(userName : String , tokenResponce : TokenResponce) {
        TokenContainer.update(tokenResponce.access_token , tokenResponce.refresh_token)
        userLocalDataSource.saveToken(tokenResponce.access_token , tokenResponce.refresh_token)
        userLocalDataSource.saveUserName(userName)
    }
}