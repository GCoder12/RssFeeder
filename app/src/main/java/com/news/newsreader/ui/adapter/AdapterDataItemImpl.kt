package com.news.newsreader.ui.adapter

class AdapterDataItemImpl(
    override val link: String,
    override val imageUrl: String,
    override val title: String,
    override val description: String,
    override val keyword: String,
    override val categoryTitle: String
) : AdapterDataItem {
}