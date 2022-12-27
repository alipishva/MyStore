package com.arp.mynikestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class NikeFragment : Fragment() , NikeView {

    override val rootView : CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext : Context?
        get() = context
}

abstract class NikeActivity : AppCompatActivity() , NikeView {

    override val rootView : CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout){
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw java.lang.IllegalStateException("RootView must be instance of CoordinatorLayout")
            }else
                return viewGroup
        }

    override val viewContext : Context?
        get() = this
}

interface NikeView {
    val viewContext : Context?
    val rootView : CoordinatorLayout?

    fun setProgressIndicator(mustShow : Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loading_view)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.view_loading , it , false)
                    it.addView(loadingView)
                }

                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }

        }
    }

}

abstract class NikeViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}