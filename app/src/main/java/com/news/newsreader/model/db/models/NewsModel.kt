package com.news.newsreader.model.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.newsreader.model.AdapterDataItem

@Entity
data class NewsModel (
    val guid : String = "",
    override val title : String = "",
    override val link : String = "",
    override val description : String = "",
    override val imageUrl : String = "",
    val parentFeedId : Long,
    @PrimaryKey(autoGenerate = true) val newsItemId : Long = 0
) : AdapterDataItem