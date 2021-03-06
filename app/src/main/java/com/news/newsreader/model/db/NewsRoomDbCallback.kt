package com.news.newsreader.model.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.news.newsreader.model.db.models.NewsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsRoomDbCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {

    override fun onOpen(db: SupportSQLiteDatabase) {
        val db = NewsRoomDatabase.INSTANCE?.let {
            scope.launch {
//                insertDummyFeeds(it.NewsDao())
            }
        }
    }

//    private suspend fun insertDummyFeeds(newsDao: NewsDao) {
//        newsDao.insert(
//            NewsModel(
//                "",
//                "NASA Provides Update on Commercial Crew Program, Close Call Review of Boeing’s Orbital Flight Test",
//                "http://www.nasa.gov/press-release/nasa-provides-update-on-commercial-crew-program-close-call-review-of-boeing-s-orbital</link>\n",
//                "NASA will host a media teleconference at 2:30 p.m. EDT Tuesday, July 7, to discuss the outcome of its High Visibility Close Call review of the December 2019 uncrewed Orbital Flight Test of Boeing’s Starliner spacecraft.",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/49351604192_f238aeaf3b_k.jpg?itok=opwSTxAY"
//            )
//        )
//        newsDao.insert(
//            NewsModel(
//                "",
//                "Artemis Generation Students Across US to Speak with NASA Astronaut in Space",
//                "http://www.nasa.gov/press-release/artemis-generation-students-across-us-to-speak-with-nasa-astronaut-in-space",
//                "Students from across the nation will pose questions about NASA’s Artemis program to an astronaut aboard the International Space Station. The educational event will air live at 12:15 p.m. EDT Thursday, July 9, on NASA Television and the agency’s website.",
//                ""
//            )
//        )
//        newsDao.insert(
//            NewsModel(
//                "",
//                "NASA Awards Total and Spectral Solar Irradiance Sensor-2 Spacecraft Contract",
//                "http://www.nasa.gov/press-release/nasa-awards-total-and-spectral-solar-irradiance-sensor-2-spacecraft-contract",
//                "NASA has awarded the Total and Spectral Solar Irradiance Sensor-2 (TSIS-2) Spacecraft contract to General Atomics Electromagnetic Systems Group of San Diego, California",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/nasa-logo-web-rgb_0.jpg?itok=mrBnB_c9"
//            )
//        )
//        newsDao.insert(
//            NewsModel(
//                "",
//                "NASA Names Joel Montalbano International Space Station Program Manager",
//                "http://www.nasa.gov/press-release/nasa-names-joel-montalbano-international-space-station-program-manager",
//                "Kathy Lueders, NASA’s associate administrator for Human Exploration and Operations, has named Joel Montalbano as manager of the International Space Station Program. The appointment was effective June 29 following the June 26 retirement of Kirk Shireman, who held the position since 2015.",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/jsc2018e049983.jpg?itok=89MwUYGn"
//            )
//        )
//        newsDao.insert(
//            NewsModel(
//                "",
//                "NASA Science Hosts Public Town Hall to Provide Updates",
//                "http://www.nasa.gov/press-release/nasa-science-hosts-public-town-hall-to-provide-updates",
//                "NASA’s Science Mission Directorate will hold a virtual community town hall with Associate Administrator for Science Thomas Zurbuchen and other members of the Science leadership team at 3 p.m. EDT Thursday, July 9, to discuss updates on the agency’s science activities.",
//                "http://www.nasa.gov/sites/default/files/styles/1x1_cardfeed/public/thumbnails/image/nasa-logo-web-rgb.jpg?itok=36eU9WU7"
//            )
//        )
//
//    }
}