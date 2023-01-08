package com.arp.mynikestore.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_DATA
import com.arp.mynikestore.data.Product
import com.arp.mynikestore.feature.common.ProductListAdapter
import com.arp.mynikestore.feature.common.VIEW_TYPE_LARGE
import com.arp.mynikestore.feature.common.VIEW_TYPE_SMALL
import com.arp.mynikestore.feature.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity() , ProductListAdapter.OnProductEventListener{

    val viewModel : ProductListViewModel by viewModel { parametersOf(intent.extras !!.getInt(EXTRA_KEY_DATA)) }
    val productListAdapter : ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val gridLayoutManager = GridLayoutManager(this , 2)

//        when user click on item transfer to ProductDetailsActivity this class implement this interface
        productListAdapter.onProductEventListener=this

        btn_view_type_changer.setOnClickListener {

            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                btn_view_type_changer.setImageResource(R.drawable.ic_list_view_24)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()

            } else {
                btn_view_type_changer.setImageResource(R.drawable.ic_grid_24)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()

            }

        }

        btn_product_list_sort.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this).setSingleChoiceItems(R.array.sortTitlesArray , viewModel.sort) { dialog , which ->
                viewModel.onSelectedSortChangeByUser(which)
                dialog.dismiss()
            }.setTitle(getString(R.string.sort))
            dialog.show()
        }

        viewModel.sortTypeListLiveData.observe(this) {
            Timber.i("selected sort type ------> $it")
            tv_selected_sort_title.text = getString(it)
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }


        viewModel.productListLiveData.observe(this) {
            productListAdapter.setData(it)
            rv_product_list.layoutManager = gridLayoutManager
            rv_product_list.adapter = productListAdapter

        }

        toolbar_product_list.onBackButtonClickListener=View.OnClickListener {
            finish()
        }
    }
//  implement on item click from Product Adapter
    override fun onProductClick(product : Product) {
    startActivity(Intent(this , ProductDetailActivity::class.java).apply {
        putExtra(EXTRA_KEY_DATA , product)
    })
    }

    override fun onFavoriteBtnClick(product : Product) {
        viewModel.addProductToFavorites(product)
    }
}