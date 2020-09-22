package com.mynewsapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    private var swipeDown : SwipeRefreshLayout? = null
    private var headingList= mutableListOf<String>()
    private var contentList=mutableListOf<String>()
    private var imageList=mutableListOf<String>()
    private var linksList=mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeDown = swipeRefresh
        swipeDown?.setOnRefreshListener {
            makeRequest(3)
        }
        makeRequest(0)
    }

    private fun setScreen(){
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = NewsListAdapter(headingList,contentList,imageList,linksList)
        recyclerView.setBackgroundColor(Color.rgb(255,255,255))
    }

    private fun addNews(heading:String,content:String,image:String,link:String){
        headingList.add(heading)
        contentList.add(content)
        imageList.add(image)
        linksList.add(link)
    }
    private fun reset(){
        headingList.clear()
        contentList.clear()
        imageList.clear()
        linksList.clear()
    }

    private fun badNetwork(){
        Toast.makeText(applicationContext,"Bad Network :-/",Toast.LENGTH_SHORT).show()
    }

    private fun makeRequest(depth : Int) {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIrequest::class.java)

        if(swipeDown?.isRefreshing == false)
        progressBar.visibility=View.VISIBLE

        GlobalScope.launch(Dispatchers.IO){

            try {
                val response = api.getNews()
                reset()
                for (i in response.news)
                    addNews(i.title,i.description,i.image,i.url)

                withContext(Dispatchers.Main){
                    setScreen()
                    if(swipeDown?.isRefreshing == false)
                        progressBar.visibility=View.GONE
                    if(swipeDown?.isRefreshing == true)
                        swipeDown?.isRefreshing = false
                }
            }
            catch (e : Exception){

                withContext(Dispatchers.Main){

                    if(swipeDown?.isRefreshing == false){
                        countDownTimer= object : CountDownTimer(5000,1000) {
                            override fun onTick(millisUntilFinished: Long) { }
                            override fun onFinish() {
                                makeRequest(0)
                                countDownTimer.cancel()
                            }
                        }
                        countDownTimer.start()
                    }
                    else{

                        if(depth!=0){
                            countDownTimer= object : CountDownTimer(5000,1000) {
                                override fun onTick(millisUntilFinished: Long) { }
                                override fun onFinish() {
                                    makeRequest(depth-1)
                                    countDownTimer.cancel()
                                }
                            }
                            countDownTimer.start()
                        }
                        else
                        {
                            swipeDown?.isRefreshing=false
                            badNetwork()
                        }
                    }
                }
            }

        }

    }
}