package com.arp.mynikestore.feature.product

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.asyncNetworkRequest
import com.arp.mynikestore.data.Comment
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.CommentRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductDetailViewModel(bundle : Bundle , commentRepository : CommentRepository) : NikeViewModel() {

    private val _productDetailLiveData = MutableLiveData<Product>()
    val productDetailLiveData : LiveData<Product>
        get() = _productDetailLiveData

    private val _commentLiveData = MutableLiveData<List<Comment>>()
    val commentLiveData : LiveData<List<Comment>>
        get() = _commentLiveData

    init {
        progressBarLiveData.value = true
        _productDetailLiveData.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(EXTRA_KEY_DATA , Product::class.java) ?: throw IllegalStateException("Product can not be null")
        } else {
            bundle.getParcelable(EXTRA_KEY_DATA) ?: throw IllegalStateException("Product can not be null")

        }

        commentRepository.getAll(productDetailLiveData.value !!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t : List<Comment>) {
                    _commentLiveData.value = t
                }

            })


    }

}