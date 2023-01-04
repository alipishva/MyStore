package com.arp.mynikestore.data

data class CartItem(val cart_item_id : Int , var count : Int , val product : Product , var changeCountProgressBarVisibility : Boolean = false)
