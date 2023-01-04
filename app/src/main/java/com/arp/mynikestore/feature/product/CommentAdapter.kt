package com.arp.mynikestore.feature.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arp.mynikestore.R
import com.arp.mynikestore.data.Comment

class CommentAdapter(private val showAll : Boolean = false) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var comments = ArrayList<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment , parent , false))
    }

    override fun onBindViewHolder(holder : CommentViewHolder , position : Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount() : Int {
        if (comments.size > 5 && ! showAll) {
            return 5
        } else {
            return comments.size
        }
    }

    class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle : TextView = itemView.findViewById<TextView>(R.id.tv_item_comment_title)
        private val tvDate : TextView = itemView.findViewById<TextView>(R.id.tv_item_comment_date)
        private val tvAuthor : TextView = itemView.findViewById<TextView>(R.id.tv_item_comment_author)
        private val tvContent : TextView = itemView.findViewById<TextView>(R.id.tv_item_comment_content)


        fun bindComment(comment : Comment) {
            tvTitle.text = comment.title
            tvDate.text = comment.date
            tvAuthor.text = comment.author.email
            tvContent.text = comment.content
        }

    }
}