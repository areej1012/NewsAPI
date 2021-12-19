package com.example.newsapi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class XmlParser {
    private val listNews = ArrayList<News>()

    var title: String = ""
    var description: String = ""
    var image: String = ""
    var date: String =""
    var link: String= ""
    @RequiresApi(Build.VERSION_CODES.O)
    fun parse(): List<News> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            val url = URL("https://news.un.org/feed/subscribe/en/news/all/rss.xml")
            parser.setInput(url.openStream(), null)
            var insideEntry = false
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.name.equals("item",true)) {
                        insideEntry = true;
                    } else if (parser.name.equals("title",true)) {
                        if (insideEntry)
                            title = parser.nextText()
                    } else if (parser.name.equals("link",true)) {
                        if (insideEntry)
                            link = parser.nextText()
                    }
                    else if (parser.name.equals("description", true)) {
                        if (insideEntry)
                            description = parser.nextText()
                    }
                    else if (parser.name.equals("pubdate", true)) {
                        if (insideEntry)
                            date = parser.nextText()
                        date = formatterDate(date)
                    }
                    else if (parser.name.equals("enclosure", true)) {
                        if (insideEntry) {
                            image = parser.getAttributeValue(0)
                        }
                    }


                } else if (eventType == XmlPullParser.END_TAG && parser.name.equals("item",true)) {
                    insideEntry = false;
                    listNews.add(News(title,description,image,date,link))

                }

                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return listNews
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatterDate(date : String) : String {
        val date = Date(date)
        val current = LocalDateTime.now()
        var formatterNow = SimpleDateFormat("dd", Locale.ENGLISH)

        val formatter = DateTimeFormatter.ofPattern("dd")
        println(formatterNow.format(date))
        println(formatter.format(current))
        if (formatterNow.format(date).equals(formatter.format(current))){
            return "Today"
        }
        else if (formatterNow.format(date).equals((formatter.format(current).toInt()-1).toString())){
            return "Yesterday"
        }
        formatterNow = SimpleDateFormat("dd-mm-yyyy")
        return formatterNow.format(date)
    }
}