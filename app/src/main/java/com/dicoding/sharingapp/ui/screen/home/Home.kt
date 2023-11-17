package com.dicoding.sharingapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.dicoding.sharingapp.di.Injection
import com.dicoding.sharingapp.ui.ViewModelFactory
import com.dicoding.sharingapp.ui.common.UiState
import com.dicoding.sharingapp.ui.components.BlankText
import com.dicoding.sharingapp.ui.components.ListComponent
import com.dicoding.sharingapp.ui.components.SearchBarComp

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllPost()
            }
            is UiState.Success -> {
                Scaffold (
                    topBar = {
                        SearchBarComp(
                            query = query,
                            onQueryChange = viewModel::search,
                            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                        )
                    },
                ) {
                    if (uiState.data.isNotEmpty()) {
                        ListComponent(
                            postPhoto = uiState.data,
                            modifier = modifier
                                .padding(it)
                                .testTag("PostList"),
                            navigateToDetail = navigateToDetail,
                            onClick = { postId ->
                                viewModel.addPostToFav(postId)
                            }
                        )
                    } else {
                        BlankText()
                    }
                }

            }
            is UiState.Error -> {}
        }
    }
}