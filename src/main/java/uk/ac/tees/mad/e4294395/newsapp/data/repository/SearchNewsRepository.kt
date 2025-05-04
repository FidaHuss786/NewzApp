package uk.ac.tees.mad.e4294395.newsapp.data.repository

import uk.ac.tees.mad.e4294395.newsapp.data.api.NetworkService
import uk.ac.tees.mad.e4294395.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchNewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getSearchNews(q: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getEverything(q))
        }.map{
            it.articles
        }
    }
}