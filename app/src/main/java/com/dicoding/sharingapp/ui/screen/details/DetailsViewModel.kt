package com.dicoding.sharingapp.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sharingapp.data.Repository
import com.dicoding.sharingapp.data.model.PostPhoto
import com.dicoding.sharingapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsPostViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<PostPhoto>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<PostPhoto>>
        get() = _uiState

    fun getPostById(postId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPostById(postId))
        }
    }

    fun addToFav(postId: Long) {
        viewModelScope.launch {
            repository.addFavPost(postId)
        }
    }
}