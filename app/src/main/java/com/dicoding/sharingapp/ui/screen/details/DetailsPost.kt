package com.dicoding.sharingapp.ui.screen.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.sharingapp.R
import com.dicoding.sharingapp.di.Injection
import com.dicoding.sharingapp.ui.ViewModelFactory
import com.dicoding.sharingapp.ui.common.UiState
import com.dicoding.sharingapp.ui.theme.SharingAppTheme

@Composable
fun DetailsPost(
    postId: Long,
    viewModel: DetailsPostViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getPostById(postId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailsComponent(
                    data.photo.photoUrl,
                    data.photo.name,
                    data.photo.desc,
                    data.isFav.value,
                    onBackClick = navigateBack,
                    onFavClick = {
                        viewModel.addToFav(postId)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailsComponent(
    image: String,
    title: String,
    desc: String,
    isFav: Boolean,
    onBackClick: () -> Unit,
    onFavClick: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFavClick()
                },
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFav) "Remove from Favorite" else "Add to Favorite",
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { parentPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(parentPadding)
                .padding(bottom = 50.dp)
        ) {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0x880000FF)),
                                startY = 0f,
                                endY = 200f
                            )
                        ),
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .animateContentSize()
                )

                Text(
                    text = desc,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .animateContentSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailsComponentPreview() {
    SharingAppTheme {
        DetailsComponent(
            image = "https://images.unsplash.com/photo-1682687982468-4584ff11f88a?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Sample Title",
            desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            isFav = false,
            onBackClick = { },
            onFavClick = { }
        )
    }
}
