package uk.ac.tees.mad.e4294395.newsapp.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.ac.tees.mad.e4294395.newsapp.data.repository.TopHeadlineRepository
import uk.ac.tees.mad.e4294395.newsapp.ui.base.UiState
import uk.ac.tees.mad.e4294395.newsapp.utils.AppConstant
import uk.ac.tees.mad.e4294395.newsapp.utils.DispatcherProvider
import uk.ac.tees.mad.e4294395.newsapp.utils.internetcheck.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import uk.ac.tees.mad.e4294395.newsapp.data.roomdatabase.entity.ArticleEntity
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ArticleEntity>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ArticleEntity>>> = _uiState

    init {
        if (networkHelper.isInternetConnected()) {
            fetchNews()
        } else {
            fetchNewsFromDB()
        }
    }

    fun fetchNews() {
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(AppConstant.DEFAULT_COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch {e ->
                    _uiState.value = UiState.Error(e)
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchNewsFromDB() {
        viewModelScope.launch {
            topHeadlineRepository.getArticlesDirectlyFromDB()
                .flowOn(Dispatchers.IO)
                .catch {
                    _uiState.value = UiState.Error(it)
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}