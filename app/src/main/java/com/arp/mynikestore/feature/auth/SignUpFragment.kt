package com.arp.mynikestore.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arp.mynikestore.R
import com.arp.mynikestore.common.NikeCompletableObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.et_fragment_login_password
import kotlinx.android.synthetic.main.fragment_login.et_fragment_signup_email
import kotlinx.android.synthetic.main.fragment_signup.*
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment() {

    private val viewModel : AuthViewModel by inject()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?) : View? {

        return inflater.inflate(R.layout.fragment_signup , container , false)
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)



        btn_fragment_signup.setOnClickListener{
            viewModel.signUp(et_fragment_signup_email.text.toString() , et_fragment_login_password.text.toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        requireActivity().finish()
                    }
                })
        }

        btn_signIn_link.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container , LoginFragment())
            }.commit()
        }

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}