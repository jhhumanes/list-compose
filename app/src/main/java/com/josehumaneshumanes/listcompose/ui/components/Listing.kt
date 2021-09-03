package com.josehumaneshumanes.listcompose.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.josehumaneshumanes.listcompose.data.PAGE_SIZE
import com.josehumaneshumanes.listcompose.domain.ListItem
import com.josehumaneshumanes.listcompose.ui.theme.AccentColor
import com.josehumaneshumanes.listcompose.ui.theme.DarkColor

@ExperimentalFoundationApi
@Composable
fun Listing(
    items: List<ListItem>,
    loading: Boolean,
    page: Int,
    getNextPage: () -> Unit,
    onChangeScrollPosition: (Int) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(items.size) { index ->
                items[index]?.let {
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        getNextPage()
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(width = 1.dp, AccentColor),
                        text = it.title,
                        color = DarkColor,
                        textAlign = TextAlign.Center,

                        )
                }
            }
        },
        contentPadding = PaddingValues(8.dp)
    )
}
