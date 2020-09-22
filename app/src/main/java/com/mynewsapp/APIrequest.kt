package com.mynewsapp

import com.mynewsapp.api.NewsApiJson
import retrofit2.http.GET

interface APIrequest {

    @GET("/v1/latest-news?language=en&apiKey=4PIjlfW48GXnNO1KnrFOCLHr0rhEq64cMzz0GqQiy6L6yMoP")
    suspend fun getNews() : NewsApiJson
}