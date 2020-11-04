package com.yogesh.newsapp.data

import java.sql.ClientInfoStatus

class MyArticle (
    val status: String,
    val totalResults:Int,
    val articles: List<Articles>
)

