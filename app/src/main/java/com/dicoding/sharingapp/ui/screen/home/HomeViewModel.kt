package com.dicoding.sharingapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sharingapp.data.Repository
import com.dicoding.sharingapp.data.model.PostPhoto
import com.dicoding.sharingapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<PostPhoto>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<PostPhoto>>>
        get() = _uiState

    fun getAllPost() {
        viewModelScope.launch {
            repository.getAllPost()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun addPostToFav(postId: Long) {
        viewModelScope.launch {
            repository.addFavPost(postId)
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.searchPost(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}

