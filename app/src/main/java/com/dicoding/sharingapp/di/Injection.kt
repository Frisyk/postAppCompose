package com.dicoding.sharingapp.di

import com.dicoding.sharingapp.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}