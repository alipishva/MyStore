package com.arp.mynikestore.data.repo.source

import com.arp.mynikestore.data.Comment
import com.arp.mynikestore.services.http.ApiService
import io.reactivex.rxjava3.core.Single

class CommentRemoteDataSource(private val apiService : ApiService) : CommentDataSource {
    override fun getAll(productId : Int) : Single<List<Comment>> = apiService.getCommentList(productId)

    override fun insert() : Single<Comment> {
        TODO("Not yet implemented")
    }
}