package com.bikcodeh.newsapp.domain.model

import com.bikcodeh.newsapp.R

data class News(
    val id: Int,
    val image: Int = R.drawable.breaking_news,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)
