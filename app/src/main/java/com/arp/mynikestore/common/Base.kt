package com.arp.mynikestore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arp.mynikestore.common.NikeException
import com.arp.mynikestore.feature.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeFragment : Fragment() , NikeView {

    override val rootView : CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val viewContext : Context?
        get() = context

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}

abstract class NikeActivity : AppCompatActivity() , NikeView {

    override val rootView : CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout) return it
                }
                throw java.lang.IllegalStateException("RootView must be instance of CoordinatorLayout")
            } else return viewGroup
        }

    override val viewContext : Context?
        get() = this

    override fun onStart() {
        super.onStart()
        if (! EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}

interface NikeView {
    val viewContext : Context?
    val rootView : CoordinatorLayout?

    fun setProgressIndicator(mustShow : Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loading_view)
                if (loadingView == null && mustShow) {
                    loadingView = LayoutInflater.from(context).inflate(R.layout.view_loading , it , false)
                    it.addView(loadingView)
                }

                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeException : NikeException) {
        viewContext?.let {
            when (nikeException.type) {

                NikeException.Type.SIMPLE -> {
                    showSnackBar(nikeException.serverMessage ?: it.getString(nikeException.userFriendlyMessage))
                }

                NikeException.Type.AUTH -> {
                    it.startActivity(Intent(it , AuthActivity::class.java))
                    Toast.makeText(it , "${nikeException.serverMessage}" , Toast.LENGTH_SHORT).show()
                    return
                }

                NikeException.Type.DIALOG -> {
                    showSnackBar("DIALOG")
                }

            }
        }
    }

    fun showSnackBar(message : String , duration : Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it , message , duration).show()
        }
    }

    fun showEmptySate(layoutResId : Int) : View? {
        rootView?.let {
            viewContext?.let { context ->
                var emptyState = it.findViewById<View>(R.id.emptyStateRootView)
                if (emptyState == null) {
                    emptyState = LayoutInflater.from(context).inflate(layoutResId , it , false)
                    it.addView(emptyState)
                }
                emptyState.visibility = View.VISIBLE
                return emptyState
            }
        }
        return null
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