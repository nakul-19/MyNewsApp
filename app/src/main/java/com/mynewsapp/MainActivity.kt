package com.mynewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val news= listOf<String>("X died","Y died","2020 sucks","enough of this year already",
            "X died","Y died","2020 sucks","enough of this year already",
            "X died","Y died","2020 sucks","enough of this year already",
            "X died","Y died","2020 sucks","enough of this year already",
            "X died","Y died","2020 sucks","enough of this year already",
            "X died","Y died","2020 sucks","enough of this year already",
            "X died","Y died","2020 sucks","enough of this year already")
        val newsList = findViewById<RecyclerView>(R.id.my_recycler_view)
        newsList.layoutManager = LinearLayoutManager(this)
        newsList.adapter = NewsListAdapter(news)
    }
}