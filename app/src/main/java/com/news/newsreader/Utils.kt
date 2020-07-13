package com.news.newsreader

import android.content.Context
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.models.NewsCategoryModel
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

    init {

    }

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
            ApiService.CATEGORY_FEED_1 -> Constants.FEED_FILENAME_1
            ApiService.CATEGORY_FEED_2 -> Constants.FEED_FILENAME_2
            ApiService.CATEGORY_FEED_3 -> Constants.FEED_FILENAME_3
            ApiService.CATEGORY_FEED_4 -> Constants.FEED_FILENAME_4
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

//        fun getFirstTestItem(category: String) : NewsModel {
//            return NewsModel(
//                "",
//                "NASA Provides Update on Commercial Crew Program, Close Call Review of Boeing’s Orbital Flight Test",
//                "http://www.nasa.gov/press-release/nasa-provides-update-on-commercial-crew-program-close-call-review-of-boeing-s-orbital</link>\n",
//                "NASA will host a media teleconference at 2:30 p.m. EDT Tuesday, July 7, to discuss the outcome of its High Visibility Close Call review of the December 2019 uncrewed Orbital Flight Test of Boeing’s Starliner spacecraft.",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/49351604192_f238aeaf3b_k.jpg?itok=opwSTxAY",
//                category.hashCode().toLong()
//            )
//        }
//
//        fun getSecondTestItem(category: String) : NewsModel {
//            return NewsModel(
//                "",
//                "Artemis Generation Students Across US to Speak with NASA Astronaut in Space",
//                "http://www.nasa.gov/press-release/artemis-generation-students-across-us-to-speak-with-nasa-astronaut-in-space",
//                "Students from across the nation will pose questions about NASA’s Artemis program to an astronaut aboard the International Space Station. The educational event will air live at 12:15 p.m. EDT Thursday, July 9, on NASA Television and the agency’s website.",
//                "",
//                category.hashCode().toLong()
//            )
//        }
//
//        fun getThirdTestItem(category: String): NewsModel {
//            return NewsModel(
//                "",
//                "NASA Awards Total and Spectral Solar Irradiance Sensor-2 Spacecraft Contract",
//                "http://www.nasa.gov/press-release/nasa-awards-total-and-spectral-solar-irradiance-sensor-2-spacecraft-contract",
//                "NASA has awarded the Total and Spectral Solar Irradiance Sensor-2 (TSIS-2) Spacecraft contract to General Atomics Electromagnetic Systems Group of San Diego, California",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/nasa-logo-web-rgb_0.jpg?itok=mrBnB_c9",
//                category.hashCode().toLong()
//            )
//        }
//
//        fun getFourthTestItem(category: String): NewsModel {
//            return NewsModel(
//                "",
//                "NASA Names Joel Montalbano International Space Station Program Manager",
//                "http://www.nasa.gov/press-release/nasa-names-joel-montalbano-international-space-station-program-manager",
//                "Kathy Lueders, NASA’s associate administrator for Human Exploration and Operations, has named Joel Montalbano as manager of the International Space Station Program. The appointment was effective June 29 following the June 26 retirement of Kirk Shireman, who held the position since 2015.",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/jsc2018e049983.jpg?itok=89MwUYGn",
//                category.hashCode().toLong()
//            )
//        }
//
//        fun getFifthTestItem(category: String) : NewsModel {
//            return NewsModel(
//                "",
//                "NASA Science Hosts Public Town Hall to Provide Updates",
//                "http://www.nasa.gov/press-release/nasa-science-hosts-public-town-hall-to-provide-updates",
//                "NASA’s Science Mission Directorate will hold a virtual community town hall with Associate Administrator for Science Thomas Zurbuchen and other members of the Science leadership team at 3 p.m. EDT Thursday, July 9, to discuss updates on the agency’s science activities.",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/nasa-logo-web-rgb.jpg?itok=36eU9WU7",
//                category.hashCode().toLong()
//            )
//        }



//            return arrayListOf(getFirstTestItem(category),
//                getSecondTestItem(category),
//                getThirdTestItem(category),
//                getFourthTestItem(category),
//                getFifthTestItem(category))
//        }

    }
}

