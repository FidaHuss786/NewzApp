package uk.ac.tees.mad.e4294395.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsSourcesResponse (
    @SerializedName("status")
    val status: String = "",
    @SerializedName("sources")
    val sources: List<NewsSource> = ArrayList()
)