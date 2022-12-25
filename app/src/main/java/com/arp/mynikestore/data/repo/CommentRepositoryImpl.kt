package com.arp.mynikestore.data.repo

import com.arp.mynikestore.data.Comment
import com.arp.mynikestore.data.repo.source.CommentDataSource
import io.reactivex.rxjava3.core.Single

class CommentRepositoryImpl(private val commentRemoteDataSource : CommentDataSource) : CommentRepository {

    override fun getAll(productId : Int) : Single<List<Comment>> = commentRemoteDataSource.getAll(productId)

    override fun insert() : Single<Comment> {
        TODO("Not yet implemented")
    }
}