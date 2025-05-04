package uk.ac.tees.mad.e4294395.newsapp.ui.newssources

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.ac.tees.mad.e4294395.R
import uk.ac.tees.mad.e4294395.newsapp.data.model.NewsSource
import uk.ac.tees.mad.e4294395.newsapp.ui.base.UiState
import uk.ac.tees.mad.e4294395.newsapp.ui.common.ShowErrorView
import uk.ac.tees.mad.e4294395.newsapp.ui.common.ShowLoading
import uk.ac.tees.mad.e4294395.newsapp.ui.common.SourceUI
import uk.ac.tees.mad.e4294395.newsapp.ui.common.TopAppBarWithOutIconUI
import uk.ac.tees.mad.e4294395.newsapp.utils.NoInternetException

@Composable
fun NewsSourceRoute(
    onSourceClick: (sourceId: String) -> Unit,
    viewModel: SourcesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBarWithOutIconUI(title = stringResource(id = R.string.new_sources))
        }, content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                NewsSourceScreen(uiState, onSourceClick, viewModel)
            }
        })
}

@Composable
fun NewsSourceScreen(
    uiState: UiState<List<NewsSource>>,
    onSourceClick: (sourceId: String) -> Unit,
    viewModel: SourcesViewModel
) {
    when (uiState) {
        is UiState.Success -> {
            NewsSourceList(uiState.data, onSourceClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            var errorText = stringResource(id = R.string.something_went_wrong)
            if (uiState.throwable is NoInternetException) {
                errorText = stringResource(id = R.string.network_error)
            }
            ShowErrorView(
                text = errorText
            ) {
                viewModel.fetchNewsSources()
            }
        }

    }
}

@Composable
fun NewsSourceList(sources: List<NewsSource>, onSourceClick: (sourceId: String) -> Unit) {
    LazyColumn {
        items(sources, key = { source -> source.id!! }) { source ->
            SourceUI(source, onSourceClick)
        }
    }
}


