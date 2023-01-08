package com.arp.mynikestore.feature.product.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.Comment
import com.arp.mynikestore.data.repo.comment.CommentRepository

class CommentListViewModel(productId : Int , commentRepository : CommentRepository) : NikeViewModel() {
    private val _commentsLiveData = MutableLiveData<List<Comment>>()
    val commentsLiveData : LiveData<List<Comment>>
        get() = _commentsLiveData


    init {
        progressBarLiveData.value = true
        commentRepository.getAll(productId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t : List<Comment>) {
                    _commentsLiveData.value = t
                }
            })
    }
}