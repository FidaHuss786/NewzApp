package uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<ArticleEntity>>

    @Insert
    fun insertAll(article: List<ArticleEntity>)

    @Query("DELETE FROM article")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(articles: List<ArticleEntity>) {
        deleteAll()
        return insertAll(articles)
    }
}