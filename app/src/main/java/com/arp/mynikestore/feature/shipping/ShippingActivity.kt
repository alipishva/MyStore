package com.arp.mynikestore.feature.shipping

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.common.NikeSingleObserver
import com.arp.mynikestore.common.openUrlInCustomTab
import com.arp.mynikestore.data.PurchaseDetail
import com.arp.mynikestore.data.SubmitOrderResult
import com.arp.mynikestore.feature.cart.CartItemAdapter
import com.arp.mynikestore.feature.checkout.CheckOutActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShippingActivity : AppCompatActivity() {
    val viewModel : ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_KEY_DATA , PurchaseDetail::class.java) ?: throw IllegalStateException("Purchase detail cannot be null")
        } else {
            intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA) ?: throw IllegalStateException("Purchase detail cannot be null")
        }

        val viewHolder = CartItemAdapter.PurchaseDetailViewHHolder(purchaseDetailView)
        val discount = purchaseDetail.total_price - purchaseDetail.payable_price
        viewHolder.bind(purchaseDetail.total_price , purchaseDetail.shipping_cost , purchaseDetail.payable_price , discount)

        val onClickListener = View.OnClickListener {
            viewModel.submitOrder(et_shipping_first_name.text.toString() ,
                et_shipping_last_name.text.toString() ,
                et_shipping_postal_code.text.toString() ,
                et_shipping_phone_number.text.toString() ,
                et_shipping_address.text.toString() ,
                if (it.id == R.id.btn_online_payment) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable) {
                    override fun onSuccess(t : SubmitOrderResult) {
                        if (t.bank_gateway_url.isNotEmpty()) {
                            openUrlInCustomTab(this@ShippingActivity , t.bank_gateway_url)
                        } else {
                            startActivity(Intent(this@ShippingActivity , CheckOutActivity::class.java).apply {
                                putExtra(EXTRA_KEY_DATA , t.order_id)
                            })
                        }
                        finish()
                    }
                })
        }

        btn_online_payment.setOnClickListener(onClickListener)
        btn_cash_on_delivery.setOnClickListener(onClickListener)

    }
}