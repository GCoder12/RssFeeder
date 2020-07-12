package com.news.newsreader.model.db.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity()
class NewsCategoryModel(
    val category: String,
    var isDisplayed : Boolean = true,
    @PrimaryKey val feedId: Long = category.hashCode().toLong()
)