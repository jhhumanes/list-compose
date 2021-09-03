package com.josehumaneshumanes.listcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.lifecycleScope
import com.josehumaneshumanes.listcompose.data.ItemRepository
import com.josehumaneshumanes.listcompose.ui.components.util.SnackbarController
import com.josehumaneshumanes.listcompose.ui.screens.ListingScreen
import com.josehumaneshumanes.listcompose.ui.screens.ListingViewModel
import com.josehumaneshumanes.listcompose.ui.theme.ListComposeTheme

class MainActivity : ComponentActivity() {

    private val snackbarController = SnackbarController(lifecycleScope)

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListComposeTheme {
                ListingScreen(ListingViewModel(ItemRepository()), snackbarController)
            }
        }
    }
}
