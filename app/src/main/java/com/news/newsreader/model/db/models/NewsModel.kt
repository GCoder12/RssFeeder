package com.news.newsreader.model.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.newsreader.model.AdapterDataItem

@Entity(tableName = "news_item")
data class NewsModel (
    @PrimaryKey val guid : String = "",
    override val title : String = "",
    override val link : String = "",
    override val description : String = "",
    override val imageUrl : String = "",
    val parentCategory : String = ""
) : AdapterDataItem