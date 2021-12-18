package com.example.newsapi

import java.io.Serializable

class News(
    var title: String,
    var description: String,
    var image: String,
    var date: String,
    var link: String
) : Serializable {
}