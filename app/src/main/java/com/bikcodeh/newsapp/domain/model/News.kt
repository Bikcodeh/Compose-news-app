package com.bikcodeh.newsapp.domain.model

import com.bikcodeh.newsapp.R

data class News(
    val id: Int,
    val image: Int = R.drawable.ic_broken_image,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)
