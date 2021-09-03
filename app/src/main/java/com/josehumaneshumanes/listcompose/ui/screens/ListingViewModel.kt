package com.josehumaneshumanes.listcompose.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josehumaneshumanes.listcompose.data.ItemRepository
import com.josehumaneshumanes.listcompose.data.PAGE_SIZE
import com.josehumaneshumanes.listcompose.domain.ListItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListingViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _items = MutableStateFlow(emptyList<ListItem>())
    val items: StateFlow<List<ListItem>>
        get() = _items

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading

    val searching = mutableStateOf(false)
    val filtering = mutableStateOf(false)

    sealed class UiEvent {
        object SearchClicked : UiEvent()
        object FilterClicked : UiEvent()
        object SearchingEnd : UiEvent()
        object FilteringEnd : UiEvent()
    }

    val page = mutableStateOf(1)
    private var scrollPosition = 0

    init {
        getData()
    }

    fun onTriggerEvent(uiEvent: UiEvent) = when (uiEvent) {
        UiEvent.SearchClicked -> searching.value = true
        UiEvent.FilterClicked -> filtering.value = true
        UiEvent.SearchingEnd -> searching.value = false
        UiEvent.FilteringEnd -> filtering.value = false
    }


    private fun getData() {
        viewModelScope.launch {
            _loading.value = true

            delay(2000)
            val newItems = repository.getData(page.value)
            _items.value = newItems

            _loading.value = false
        }
    }

    fun getNextPage() {
        viewModelScope.launch {
            // prevent duplicate events due to recompose happening too quickly
            if ((scrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                _loading.value = true
                incrementPage()

                delay(2000)

                if (page.value > 1) {
                    val newItems = repository.getData(page.value)
                    appendItems(newItems)
                }
                _loading.value = false
            }
        }
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeScrollPosition(position: Int) {
        scrollPosition = position
    }

    private fun appendItems(newItems: List<ListItem>) {
        val current = ArrayList(_items.value)
        current.addAll(newItems)
        _items.value = current
    }

}
