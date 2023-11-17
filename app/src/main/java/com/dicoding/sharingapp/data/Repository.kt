package com.dicoding.sharingapp.data

import androidx.compose.runtime.mutableStateOf
import com.dicoding.sharingapp.data.model.FakePhotoData
import com.dicoding.sharingapp.data.model.PostPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository {

    private val postPhoto = mutableListOf<PostPhoto>()

    init {
        if (postPhoto.isEmpty()) {
            FakePhotoData.dummyPost.forEach {
                postPhoto.add(PostPhoto(it, mutableStateOf(false)))
            }
        }
    }

    fun getAllPost(): Flow<List<PostPhoto>> {
        return flowOf(postPhoto)
    }

    fun getPostById(postId: Long): PostPhoto {
        return postPhoto.first {
            it.photo.id == postId
        }
    }

    fun addFavPost(postId: Long) {
        val index = postPhoto.indexOfFirst { it.photo.id == postId }
        if (index >= 0) {
            postPhoto[index].isFav.value = !postPhoto[index].isFav.value
        }
    }

    fun getFavoritePosts(): Flow<List<PostPhoto>> {
        return flowOf(postPhoto.filter { it.isFav.value })
    }

    fun searchPost(query: String): Flow<List<PostPhoto>> {
        return flowOf(postPhoto.filter {
            it.photo.name.contains(query, ignoreCase = true)
        })
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}