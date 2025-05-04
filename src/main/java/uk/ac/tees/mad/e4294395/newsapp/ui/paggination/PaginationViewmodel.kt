package uk.ac.tees.mad.e4294395.newsapp.ui.paggination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import uk.ac.tees.mad.e4294395.newsapp.data.model.Article
import uk.ac.tees.mad.e4294395.newsapp.data.repository.TopHeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaginationViewmodel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Article>>(value = PagingData.empty())

    val uiState: StateFlow<PagingData<Article>> = _uiState

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadlinesPagination()
                .collect {
                    _uiState.value = it
                }
        }
    }

}