package com.arp.mynikestore.feature.order

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderHistoryActivity : NikeActivity() {

    val viewModel : OrderHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        orderHistoryRv.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)

        viewModel.orderHistoryLiveData.observe(this) {
            orderHistoryRv.adapter = OrderHistoryItemAdapter(this , it)
        }

    }
}