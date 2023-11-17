package com.dicoding.sharingapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dicoding.sharingapp.data.model.PostPhoto

@Composable
fun ListComponent(
    postPhoto: List<PostPhoto>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    onClick: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(postPhoto, key = { it.photo.id }) { data ->
            PostItem(
                photoUrl = data.photo.photoUrl,
                title = data.photo.name,
                isFavorite = data.isFav.value,
                onClick = {
                    onClick(data.photo.id)
                },
                modifier = Modifier
                    .clickable {
                        navigateToDetail(data.photo.id)
                    }
                    .animateContentSize()
            )
        }
    }
}
