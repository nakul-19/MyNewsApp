package com.mynewsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        val _title = intent.getStringExtra("title")
        val _desc = intent.getStringExtra("description")
        val _image = intent.getStringExtra("image")
        val _link = intent.getStringExtra("link")
        val t = findViewById<TextView>(R.id.title)
        val d = findViewById<TextView>(R.id.description)
        val i = findViewById<ImageView>(R.id.image)
        t.text = _title
        d.text = _desc
        Glide.with(i)
            .load(_image)
            .into(image)
        read_more.setOnClickListener {
            val uri = Uri.parse(_link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}