package com.arp.mynikestore.feature.checkout

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.common.formatPrice
import kotlinx.android.synthetic.main.activity_check_out.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {

    val viewModel : CheckOutViewModel by viewModel {
        val uri : Uri? = intent.data
        if (uri != null) parametersOf(uri.getQueryParameter("order_id") !!.toInt())
        else parametersOf(intent.extras !!.getInt(EXTRA_KEY_DATA))
    }


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        viewModel.checkoutLiveData.observe(this) {
            tv_checkout_order_price.text = formatPrice(it.payable_price)
            tv_checkout_order_status.text = it.payment_status
            tv_checkout_purchase_status.text= if (it.purchase_success) "Successful" else "Unsuccessful"
        }
    }
}