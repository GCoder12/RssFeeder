package com.news.newsreader.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NewsItem (
    @PrimaryKey private val guid : String,
    private val title : String,
    private val link : String,
    private val description : String,
    private val imageUrl : String
)