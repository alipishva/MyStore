package com.arp.mynikestore.feature.product.comment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.NikeActivity
import com.arp.mynikestore.R
import com.arp.mynikestore.common.EXTRA_KEY_ID
import com.arp.mynikestore.data.Comment
import com.arp.mynikestore.feature.product.CommentAdapter
import kotlinx.android.synthetic.main.activity_comment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {

    private val commentListViewModel : CommentListViewModel by viewModel { parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID)) }
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)

        commentListViewModel.commentsLiveData.observe(this){
            val adapter=CommentAdapter(true)
            rv_all_comment.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            adapter.comments=it as ArrayList<Comment>
            rv_all_comment.adapter=adapter
        }

        commentListViewModel.progressBarLiveData.observe(this){
            setProgressIndicator(it)
        }

        commentListToolbar.onBackButtonClickListener= View.OnClickListener {
            finish()
        }
    }
}