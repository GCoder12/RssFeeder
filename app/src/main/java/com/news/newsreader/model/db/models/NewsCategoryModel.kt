package com.news.newsreader.model.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NewsCategoryModel (
    @PrimaryKey val category:String
)