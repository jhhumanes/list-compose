package com.josehumaneshumanes.listcompose.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.josehumaneshumanes.listcompose.R
import com.josehumaneshumanes.listcompose.ui.components.DefaultSnackbar
import com.josehumaneshumanes.listcompose.ui.components.Listing
import com.josehumaneshumanes.listcompose.ui.components.LoadingProgressBar
import com.josehumaneshumanes.listcompose.ui.components.TopActionBar
import com.josehumaneshumanes.listcompose.ui.components.util.SnackbarController
import com.josehumaneshumanes.listcompose.ui.screens.ListingViewModel.UiEvent.*
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun ListingScreen(viewModel: ListingViewModel, snackbarController: SnackbarController) {

    val items by viewModel.items.collectAsState(emptyList())
    val loading by viewModel.loading.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val searchMessage = viewModel.searching.value
    val filterMessage = viewModel.filtering.value

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        topBar = {
            TopActionBar(
                title = stringResource(id = R.string.app_name),
                onSearchClicked = {
                    viewModel.onTriggerEvent(SearchClicked)
                },
                onFilterClicked = {
                    viewModel.onTriggerEvent(FilterClicked)
                }
            )
        },
        content = {
            Box(modifier = Modifier.background(color = MaterialTheme.colors.surface)) {
                Listing(
                    items,
                    loading,
                    viewModel.page.value,
                    viewModel::getNextPage,
                    viewModel::onChangeScrollPosition
                )
                LoadingProgressBar(isDisplayed = loading)
                DefaultSnackbar(
                    snackbarHostState = scaffoldState.snackbarHostState,
                    onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss() },
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }

            if (searchMessage) {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Buscando...",
                        actionLabel = "Ocultar"
                    )
                    viewModel.onTriggerEvent(SearchingEnd)
                }
            }

            if (filterMessage) {
                snackbarController.getScope().launch {
                    snackbarController.showSnackbar(
                        scaffoldState = scaffoldState,
                        message = "Filtrando...",
                        actionLabel = "Ocultar"
                    )
                    viewModel.onTriggerEvent(FilteringEnd)
                }
            }
        }
    )
}
