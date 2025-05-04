package uk.ac.tees.mad.e4294395.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import uk.ac.tees.mad.e4294395.newsapp.data.api.NetworkService
import uk.ac.tees.mad.e4294395.newsapp.data.model.Article
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.toArticleEntity
import uk.ac.tees.mad.e4294395.newsapp.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.DatabaseService
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.ArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getTopHeadlines(country: String): Flow<List<ArticleEntity>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles.map { article -> article.toArticleEntity() }
        }.flatMapConcat { articleEntity ->
            flow {
                emit(databaseService.deleteAllAndInsertAll(articleEntity))
            }
        }.flatMapConcat {
            databaseService.getArticles()
        }
    }

    fun getArticlesDirectlyFromDB(): Flow<List<ArticleEntity>> {
        return databaseService.getArticles()
    }

    fun getTopHeadlinesPagination(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                PaginationTopheadline(networkService)
            }
        ).flow
    }

    fun getNewsBySources(sources: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsBySources(sources))
        }.map {
            it.articles
        }
    }

    fun getNewsByCountry(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsByCountry(country))
        }.map {
            it.articles
        }
    }

    fun getNewsByLanguage(langauge: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsByLanguage(langauge))
        }.map {
            it.articles
        }
    }
}