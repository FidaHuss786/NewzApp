package uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity

import androidx.room.ColumnInfo

data class SourceEntity (
    @ColumnInfo(name = "sourceId")
    val id: String?,
    @ColumnInfo(name = "sourceName")
    val name: String = ""
)