package com.dicoding.sharingapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.sharingapp.ui.components.BottomBar
import com.dicoding.sharingapp.ui.navigation.Screen
import com.dicoding.sharingapp.ui.screen.details.DetailsPost
import com.dicoding.sharingapp.ui.screen.favorites.Favorites
import com.dicoding.sharingapp.ui.screen.home.Home
import com.dicoding.sharingapp.ui.screen.profile.User

@Composable
fun SharingApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailsPost.route) {
                BottomBar(navController)
            }
        },
    modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                Home(
                    navigateToDetail = { postId ->
                        navController.navigate(Screen.DetailsPost.createRoute(postId))
                    },
                )
            }
            composable(Screen.Favorites.route) {
                Favorites(
                    navigateToDetail = { postId ->
                        navController.navigate(Screen.DetailsPost.createRoute(postId))
                    },
                )
            }
            composable(Screen.Profile.route) {
                User()
            }
            composable(
                route = Screen.DetailsPost.route,
                arguments = listOf(navArgument("postId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("postId") ?: -1L
                DetailsPost (
                    postId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}