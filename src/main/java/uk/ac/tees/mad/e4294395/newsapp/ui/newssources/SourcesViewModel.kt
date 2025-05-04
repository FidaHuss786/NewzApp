package uk.ac.tees.mad.e4294395.newsapp.ui.newssources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.ac.tees.mad.e4294395.newsapp.data.model.NewsSource
import uk.ac.tees.mad.e4294395.newsapp.data.repository.NewsSourcesRepository
import uk.ac.tees.mad.e4294395.newsapp.ui.base.UiState
import uk.ac.tees.mad.e4294395.newsapp.utils.DispatcherProvider
import uk.ac.tees.mad.e4294395.newsapp.utils.NoInternetException
import uk.ac.tees.mad.e4294395.newsapp.utils.internetcheck.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val newsSourcesRepository: NewsSourcesRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<NewsSource>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<NewsSource>>> = _uiState

    init {
        fetchNewsSources()
    }

    fun fetchNewsSources() {
        viewModelScope.launch {
            if (networkHelper.isInternetConnected()) {
                newsSourcesRepository.getNewsSources()
                    .flowOn(dispatcherProvider.io)
                    .catch {e ->
                        _uiState.value = UiState.Error(e)
                    }.collect() {
                        _uiState.value = UiState.Success(it)
                    }
            } else {
                _uiState.value = UiState.Error(
                    throwable = NoInternetException()
                )
                return@launch
            }
        }
    }

}