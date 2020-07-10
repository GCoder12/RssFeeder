package com.news.newsreader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_item")
data class NewsItem (
    @PrimaryKey val guid : String = "",
    override val title : String = "",
    override val link : String = "",
    override val description : String = "",
    override val imageUrl : String = ""
) : AdapterDataItem