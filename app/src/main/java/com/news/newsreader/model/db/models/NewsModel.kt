package com.news.newsreader.model.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.newsreader.ui.adapter.AdapterDataItem

@Entity
data class NewsModel (
    val guid : String = "",
    val title : String = "",
    val link : String = "",
    val description : String = "",
    val imageUrl : String = "",
    val parentFeedId : Long,
    @PrimaryKey(autoGenerate = true) val newsItemId : Long = 0
)