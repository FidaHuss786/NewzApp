package uk.ac.tees.mad.e4294395.newsapp.ui.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.ac.tees.mad.e4294395.newsapp.data.model.LocaleInfo
import uk.ac.tees.mad.e4294395.newsapp.data.repository.CountriesRepository
import uk.ac.tees.mad.e4294395.newsapp.ui.base.UiState
import uk.ac.tees.mad.e4294395.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<LocaleInfo>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<LocaleInfo>>> = _uiState

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            countriesRepository.getCountries()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e)
                }.collect() {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}