package uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.ArticleEntity

interface DatabaseService {

    fun getArticles(): Flow<List<ArticleEntity>>

    fun deleteAllAndInsertAll(articleEntity: List<ArticleEntity>)
}