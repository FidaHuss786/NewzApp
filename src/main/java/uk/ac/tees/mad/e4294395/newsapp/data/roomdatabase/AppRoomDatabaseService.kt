package uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.ArticleEntity

class AppRoomDatabaseService(
    private val appRoomDataBase: AppRoomDataBase
) : DatabaseService {

    override fun getArticles(): Flow<List<ArticleEntity>> {
        return appRoomDataBase.articleDao().getAll()
    }

    override fun deleteAllAndInsertAll(articleEntity: List<ArticleEntity>) {
        return appRoomDataBase.articleDao().deleteAllAndInsertAll(articleEntity)
    }
}