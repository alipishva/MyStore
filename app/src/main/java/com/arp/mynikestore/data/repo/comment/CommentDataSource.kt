package com.arp.mynikestore.data.repo.comment

import com.arp.mynikestore.data.Comment
import io.reactivex.rxjava3.core.Single

interface CommentDataSource {

    fun getAll(productId : Int) : Single<List<Comment>>
    fun insert() : Single<Comment>
}