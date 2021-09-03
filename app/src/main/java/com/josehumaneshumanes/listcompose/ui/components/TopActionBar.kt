package com.josehumaneshumanes.listcompose.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.josehumaneshumanes.listcompose.R
import com.josehumaneshumanes.listcompose.ui.theme.AccentColor
import com.josehumaneshumanes.listcompose.ui.theme.LightColor

@Composable
fun TopActionBar(title: String, onSearchClicked: () -> Unit, onFilterClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = LightColor) },
        backgroundColor = AccentColor,
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = LightColor,
                    contentDescription = stringResource(R.string.search)
                )
            }

            IconButton(onClick = onFilterClicked) {
                Icon(
                    imageVector = Icons.Filled.List,
                    tint = LightColor,
                    contentDescription = stringResource(id = R.string.filter)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopActionBarPreview() {
    TopActionBar(title = "List Compose", onSearchClicked = {}, onFilterClicked = {})
}