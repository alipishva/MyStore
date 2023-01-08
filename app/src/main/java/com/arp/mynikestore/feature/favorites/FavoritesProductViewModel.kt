package com.arp.mynikestore.feature.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arp.mynikestore.NikeViewModel
import com.arp.mynikestore.common.NikeCompletableObserver
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.data.repo.ProductRepository
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class FavoritesProductViewModel(private val favoritesProductRepository : ProductRepository) : NikeViewModel() {

    private val _FavoritesProductLiveData = MutableLiveData<List<Product>>()
    val favoritesProductLiveData : LiveData<List<Product>>
        get() = _FavoritesProductLiveData

    init {
        favoritesProductRepository.getFavoriteProducts().subscribeOn(Schedulers.io()).subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t : List<Product>) {
                    _FavoritesProductLiveData.postValue(t)
                }
            })
    }

    fun removeProductFromFavorites(product : Product) {
        favoritesProductRepository.deleteFromFavorite(product).subscribeOn(Schedulers.io()).subscribe(object : NikeCompletableObserver(compositeDisposable) {
            override fun onComplete() {
                Timber.i("remove from Fav")
            }
        })
    }

}