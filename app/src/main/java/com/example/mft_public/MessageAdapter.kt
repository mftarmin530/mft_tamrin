package com.example.mft_public

import MessageModel
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ahad on 11/29/2024.
 */
class MessageAdapter(private val list: List<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view.findViewById<ConstraintLayout>(R.id.main)
        val nameTv = view.findViewById<TextView>(R.id.nameTv)
        val titleTv = view.findViewById<TextView>(R.id.titleTv)
        val commentTv = view.findViewById<TextView>(
            R.id.commentTv
        )
        val likeTv = view.findViewById<TextView>(R.id.likeTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.nameTv.text = item.name
        holder.titleTv.text = item.title
        holder.likeTv.text = item.likes.size.toString()
        holder.commentTv.text = item.comments.size.toString()
        holder.root.setOnClickListener {
            val intent = Intent(holder.root.context, DetailActivity::class.java)
            intent.putExtra("messageId", item.id)
            holder.root.context.startActivity(intent)
        }
    }

}