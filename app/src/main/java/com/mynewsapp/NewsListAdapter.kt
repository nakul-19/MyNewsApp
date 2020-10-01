package com.mynewsapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(var heading: List<String>,
                      var content: List<String>,
                      var images: List<String>,
                      var link: List<String>) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    inner class ViewHolder(val newsView: View) : RecyclerView.ViewHolder(newsView){
        val newsHeading = newsView.findViewById<TextView>(R.id.heading)
        val newsImage = newsView.findViewById<ImageView>(R.id.image)

        init {
            newsView.setOnClickListener {v: View? ->
                val position = adapterPosition
                val intent = Intent(newsView.context, ArticleActivity::class.java)
//                intent.data = Uri.parse(link[position])
                intent.putExtra("title",heading[position])
                intent.putExtra("description",content[position])
                intent.putExtra("image",images[position])
                intent.putExtra("link",link[position])
                startActivity(newsView.context, intent, null)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.news_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.newsHeading.text=heading[position]
        Glide.with(holder.newsImage)
            .load(images[position])
            .into(holder.newsImage)
    }

    override fun getItemCount(): Int = heading.size
}