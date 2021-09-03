package com.josehumaneshumanes.listcompose.data

import com.josehumaneshumanes.listcompose.domain.ListItem

const val PAGE_SIZE = 50

class ItemRepository {

    suspend fun getData(page: Int): List<ListItem> =
        List(PAGE_SIZE) { ListItem("Item #${it + (page - 1) * PAGE_SIZE + 1}") }

}
