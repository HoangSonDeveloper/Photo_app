package com.example.eraassignment.viewModel

import Events
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eraassignment.api.ApiService
import com.example.eraassignment.model.Image
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SearchScreenViewModel : ViewModel() {
    private val _apiService = ApiService()
    private val _events = MutableSharedFlow<Events>(replay = 0)
    private val _query = mutableStateOf("people")
    val isRefreshing = mutableStateOf(false)
    val searchTerm = mutableStateOf("people")

    private var _page = 1

    val eventFlow = _events.asSharedFlow()
    var images = mutableStateOf<List<Image>>(emptyList())
    var isLoading = mutableStateOf(false)
    val selectedImage = mutableStateOf<Image?>(null)


    fun getImages() {
        viewModelScope.launch {
            try {
                if (_page == 1) {
                    isLoading.value = true
                }
                val response = _apiService.create.getImages(_query.value, _page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        images.value += it.photos
                    }
                }
                else {
                    _events.emit(Events.ShowToast("Error when getting images ${response.message()}"))
                }
            } catch (e: Exception) {
                _events.emit(Events.ShowToast("Error when getting images ${e.message}"))
            } finally {
                isLoading.value = false
                isRefreshing.value = false
            }
        }
    }

    fun onRefresh() {
        isRefreshing.value = true
        _page = 1
        images.value = emptyList()
        getImages()
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun goToNextPage() {
        _page += 1
    }

    fun setSelectedImage(image: Image?) {
        selectedImage.value = image
    }

    fun clearImages() {
        images.value = emptyList()
        _page = 1
    }
}