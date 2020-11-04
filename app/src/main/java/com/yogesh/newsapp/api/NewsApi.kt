package com.yogesh.newsapp.api

import com.yogesh.newsapp.data.MyArticle
import retrofit2.Call
import retrofit2.http.*


interface NewsApi {

    @GET("top-headlines")
    fun getTopHighlight(
        //@QueryMap header: Map<String, String>
        @Query("country") id: String,
        @Query("apiKey") key:String,
        @Query("page") page: Int
    ): Call<MyArticle>

    @GET("everything")
    fun getEverything(
        @Query("q") id: String,
        @Query("apiKey") key:String,
        @Query("page") page: Int
    ): Call<MyArticle>

    @GET("sources")
    fun getsources(
        @Query("apiKey") key:String
    ): Call<MyArticle>


}