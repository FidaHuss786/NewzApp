package uk.ac.tees.mad.e4294395.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String = "",
)
