package com.yogesh.newsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiService {
    //https://newsapi.org/v2/top-headlines?country=in&apiKey=f08dd0c9c42b4fe8964e9e1d1b9a42d2
    //iyogi    apiKey= fbdd144b897d474d8f7aa6c48081080e
    fun onCreate(): NewsApi{
        val retrofit=Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    return retrofit.create(NewsApi::class.java)
    }
}