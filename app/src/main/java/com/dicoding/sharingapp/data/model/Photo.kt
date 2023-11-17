package com.dicoding.sharingapp.data.model

import androidx.compose.runtime.MutableState

data class Photo(
    val id: Long,
    val photoUrl: String,
    val name: String,
    val desc: String
)

data class PostPhoto(
    val photo: Photo,
    val isFav: MutableState<Boolean>
)