package com.mynewsapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://api.currentsapi.services"

class MainActivity : AppCompatActivity() {

    lateinit var countDownTimer: CountDownTimer

    private var headingList= mutableListOf<String>()
    private var contentList=mutableListOf<String>()
    private var imageList=mutableListOf<String>()
    private var linksList=mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeRequest()
    }

    private fun setScreen(){
        recyclerView.setBackgroundColor(Color.rgb(255,255,255))
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = NewsListAdapter(headingList,contentList,imageList,linksList)
    }

    private fun addNews(heading:String,content:String,image:String,link:String){
        headingList.add(heading)
        contentList.add(content)
        imageList.add(image)
        linksList.add(link)
    }

    private fun fadeIn(){
        initialScreen.animate().apply {
            alpha(0f)
            duration=3000
        }.start()
    }

    private fun makeRequest() {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIrequest::class.java)

        progressBar.visibility=View.VISIBLE

        GlobalScope.launch(Dispatchers.IO){

            try {
                val response = api.getNews()
                for (i in response.news)
                    addNews(i.title,i.description,i.image,i.url)

                withContext(Dispatchers.Main){
                    setScreen()
                    fadeIn()
                    progressBar.visibility=View.GONE
                }
            }
            catch (e : Exception){

                withContext(Dispatchers.Main){

                    countDownTimer= object : CountDownTimer(5000,1000) {
                        override fun onTick(millisUntilFinished: Long) {
                        }

                        override fun onFinish() {
                            makeRequest()
                            countDownTimer.cancel()
                        }
                    }
                    countDownTimer.start()
                }
            }

        }

    }
}