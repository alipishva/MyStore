package com.arp.mynikestore.feature.auth

import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.data.repo.UserRepository
import io.reactivex.rxjava3.core.Completable

class AuthViewModel(private val userRepository : UserRepository) : NikeViewModel() {

    fun login(email : String , password : String) : Completable {
        progressBarLiveData.value = true
        return userRepository.login(email , password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }

    fun signUp(email : String , password : String) : Completable {
        progressBarLiveData.value = true
        return userRepository.signUp(email , password).doFinally {
            progressBarLiveData.postValue(false)
        }
    }
}