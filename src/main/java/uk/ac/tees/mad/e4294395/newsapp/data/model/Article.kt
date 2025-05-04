package uk.ac.tees.mad.e4294395.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String?,
    @SerializedName("author")
    val author: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("source")
    val source: Source
)
