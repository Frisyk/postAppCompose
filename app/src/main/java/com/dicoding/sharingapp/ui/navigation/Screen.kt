package com.dicoding.sharingapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object DetailsPost : Screen("home/{postId}") {
        fun createRoute(postId: Long) = "home/$postId"
    }
}
