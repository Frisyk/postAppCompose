package com.dicoding.sharingapp.ui.screen.favorites

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.sharingapp.di.Injection
import com.dicoding.sharingapp.ui.ViewModelFactory
import com.dicoding.sharingapp.ui.common.UiState
import com.dicoding.sharingapp.ui.components.BlankText
import com.dicoding.sharingapp.ui.components.ListComponent

@Composable
fun Favorites(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavPost()
            }
            is UiState.Success -> {
                if (uiState.data.isNotEmpty()) {
                    ListComponent(
                        postPhoto = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                        onClick = { postId ->
                            viewModel.addPostToFav(postId)
                            viewModel.getFavPost()
                        }
                    )
                } else {
                    BlankText()
                }
            }
            is UiState.Error -> {
                Text("Terjadi kesalahan saat memuat data.")
            }
        }
    }
}
