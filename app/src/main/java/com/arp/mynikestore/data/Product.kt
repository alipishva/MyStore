package com.arp.mynikestore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val discount : Int ,
    val id : Int ,
    val image : String ,
    val previous_price : Int ,
    val price : Int ,
    val status : Int ,
    val title : String
):Parcelable

const val SORT_LATEST_PRODUCT = 0
const val SORT_POPULAR_PRODUCT = 1
const val SORT_PRICE_DESC_PRODUCT = 2
const val SORT_PRICE_ASC_PRODUCT = 3