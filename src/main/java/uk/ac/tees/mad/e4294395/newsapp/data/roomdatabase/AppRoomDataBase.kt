package uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.dao.ArticleDao
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDataBase  : RoomDatabase(){
    abstract fun articleDao(): ArticleDao
}