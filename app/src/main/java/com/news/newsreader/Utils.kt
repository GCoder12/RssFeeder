package com.news.newsreader

import android.content.Context
import com.news.newsreader.model.api.RemoteDataSource
import com.news.newsreader.model.db.models.NewsModel
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class Constants {
    companion object {
        val KEY_EXTRA_AVAILABLE_CATEGORIES = "categories"
        val KEY_EXTRA_SELECTED_CATEGORIES = "categories_selected"

        val FEED_FILENAME_1 = "nasafeed_breaknews.json"
        val FEED_FILENAME_2 = "buzzfeed_latest.json"
        val FEED_FILENAME_3 = "buzzfeed_lol.json"
        val FEED_FILENAME_4 = "buzzfeed_animals.json"
    }
}
class StrUtil {
    companion object {
        fun notEmpty(str:String) : Boolean {
            return str!=null && str.isNotEmpty()
        }
    }
}
class DataHelper(val context: Context) {

    fun getFileInputStream(fileName: String) : InputStreamReader {
        return InputStreamReader(
            context.assets.open(fileName))
    }

    private fun parseJson(fileName : String, category: String) : List<NewsModel> {
        val bufferedReader = BufferedReader(getFileInputStream(fileName))
        val buffer = StringBuffer()
        var c = bufferedReader.read()

        do {
            buffer.append(c.toChar())
            c = bufferedReader.read()
        } while (c!=-1)

        val json = JSONObject(buffer.toString())
        val channel = json.getJSONObject("channel")
        val items = channel.getJSONArray("item")
        val max = Math.min(items.length(),5)-1
        val list : MutableList<NewsModel> = mutableListOf()
        for (i in 0..max) {
            val item = items.getJSONObject(i)
            val thumbnail = if (item.has("thumbnail")) {
                item.getJSONObject("thumbnail").get("url")
            } else if (item.has("enclosure")) {
                item.getJSONObject("enclosure").get("url")
            } else ""
            val feedTitle = item.get("title").toString()
            list.add(NewsModel(title = feedTitle,imageUrl = thumbnail.toString(), parentFeedId = category.hashCode().toLong()))
//            println("item title is $feedTitle thumbnail $thumbnail")
        }
        return list
    }

    fun getFileNameForCategory(category: String) : String{
        return when (category) {
            RemoteDataSource.CATEGORY_FEED_1 -> Constants.FEED_FILENAME_1
            RemoteDataSource.CATEGORY_FEED_2 -> Constants.FEED_FILENAME_2
            RemoteDataSource.CATEGORY_FEED_3 -> Constants.FEED_FILENAME_3
            RemoteDataSource.CATEGORY_FEED_4 -> Constants.FEED_FILENAME_4
            else -> ""
        }
    }

    fun getTestingListRow(category: String) : List<NewsModel> {
        val fileName = getFileNameForCategory(category)
        return parseJson(fileName,category)
    }

    companion object {

        var INSTANCE : DataHelper? = null

        fun getInstance(context: Context) : DataHelper {
            val instance = INSTANCE
            if (instance!=null)
                return instance
            val dataHelper = DataHelper(context)
            INSTANCE = dataHelper
            return dataHelper
        }

    }
}

