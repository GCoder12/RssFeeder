package com.news.newsreader.model.db.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class CategoryWithNews (
    @Embedded val newsCategoryModel : NewsCategoryModel,
    @Relation(
        parentColumn = "category",
        entityColumn = "parentCategory"
    )
    val newsModel : List<NewsModel>
)