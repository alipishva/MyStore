package com.arp.mynikestore.feature.profile

import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.data.TokenContainer
import com.arp.mynikestore.data.repo.UserRepository

class ProfileViewModel(val userRepository : UserRepository) : NikeViewModel() {

    val userName : String
        get() = userRepository.getUserName()

    val isSignedIn : Boolean
        get() = TokenContainer.token != null


    fun signOut() = userRepository.signOut()


}