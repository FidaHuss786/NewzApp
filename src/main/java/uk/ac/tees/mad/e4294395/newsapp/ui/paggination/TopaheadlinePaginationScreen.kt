package me.hardi.newsapp.ui.paggination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import uk.ac.tees.mad.e4294395.newsapp.data.model.Article
import uk.ac.tees.mad.e4294395.newsapp.ui.common.ArticleUI
import uk.ac.tees.mad.e4294395.newsapp.ui.common.ShowError
import uk.ac.tees.mad.e4294395.newsapp.ui.common.ShowLoading
import uk.ac.tees.mad.e4294395.newsapp.ui.common.TopAppBarWithBackIconUI
import uk.ac.tees.mad.e4294395.newsapp.ui.paggination.PaginationViewmodel

@Composable
fun PaginationArticleRoute(
    onBackClick: () -> Unit,
    onNewsClick: (url: String) -> Unit,
    viewModel: PaginationViewmodel = hiltViewModel()
) {
    val article = viewModel.uiState.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBarWithBackIconUI(
            title = "Pagination Article"
        ) {
            onBackClick()
        }
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            PaginationScreen(article, onNewsClick)
        }
    })
}

@Composable
fun PaginationScreen(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {

    ArticleList(articles, onNewsClick)

    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = articles.loadState.refresh as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = articles.loadState.append as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }
        }
    }
}


@Composable
fun ArticleList(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles.itemCount, key = { index -> articles[index]!!.url }) { index ->
            ArticleUI(articles[index]!!, onNewsClick)
        }
    }
}
