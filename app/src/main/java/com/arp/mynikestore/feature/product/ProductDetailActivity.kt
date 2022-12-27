package com.arp.mynikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.common.EXTRA_KEY_ID
import com.arp.mynikestore.common.formatPrice
import com.arp.mynikestore.data.Comment
import com.arp.mynikestore.feature.product.comment.CommentListActivity
import com.arp.mynikestore.services.ImageLoadingService
import com.arp.mynikestore.view.scroll.ObservableScrollViewCallbacks
import com.arp.mynikestore.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : NikeActivity() {

    private val productDetailViewModel : ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    private val imageLoadingService : ImageLoadingService by inject()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        btn_product_details_back.setOnClickListener {
            finish()
        }



        rv_comments.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false)
        val commentAdapter = CommentAdapter()

        productDetailViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        productDetailViewModel.productDetailLiveData.observe(this) {

            imageLoadingService.load(iv_product_detail_image , it.image)
            tv_product_details_title.text = it.title

            tv_product_details_previous_price.apply {
                text = formatPrice(it.previous_price)
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            tv_product_details_current_Price.text = formatPrice(it.price)
            tv_product_details_toolBar_title.text = it.title
        }

        productDetailViewModel.commentLiveData.observe(this) {
            commentAdapter.comments = it as ArrayList<Comment>
            rv_comments.adapter = commentAdapter
            if (it.size > 3) {
                btn_product_detail_show_all_comment.visibility = View.VISIBLE
                btn_product_detail_show_all_comment.setOnClickListener {
                    startActivity(Intent(this , CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID , productDetailViewModel.productDetailLiveData.value !!.id)
                    })
                }
            }
        }



        initViews()


    }

    private fun initViews() {


        iv_product_detail_image.post {
            val ivProductHeight = iv_product_detail_image.height
            val toolBar = toolBar_product_detail_view
            val productImageView = iv_product_detail_image
            Observable_scroll_view.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(scrollY : Int , firstScroll : Boolean , dragging : Boolean) {
                    toolBar.alpha = scrollY.toFloat() / ivProductHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }


                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState : ScrollState?) {

                }

            })
        }
    }
}