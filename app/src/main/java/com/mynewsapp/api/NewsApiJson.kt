package com.mynewsapp.api

data class NewsApiJson(
    val news: List<New>,
    val page: Int,
    val status: String
)