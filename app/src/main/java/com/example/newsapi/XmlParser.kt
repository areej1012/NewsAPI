package com.example.newsapi

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

class XmlParser {
    private val listNews = ArrayList<News>()

    var title: String = ""
    var description: String = ""
    var image: String = ""
    var date: String =""
    var link: String= ""
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
}