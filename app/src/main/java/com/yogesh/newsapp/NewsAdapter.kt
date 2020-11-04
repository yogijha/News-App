package com.yogesh.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yogesh.newsapp.data.Articles
import kotlinx.android.synthetic.main.view_holder_layout.view.*

class NewsAdapter(private val newsData:List<Articles>, private val cellClickListener: CellClickListener)
    :RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val imageView: ImageView =view.imageView
        val title: TextView =view.title
        val content: TextView =view.content
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.view_holder_layout,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount() = newsData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = newsData[position]
        holder.title.text=data.title
        holder.content.text=data.description
        Picasso.get().load(data.urlToImage).into(holder.imageView)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data)
        }
    }
}