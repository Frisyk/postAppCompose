package com.dicoding.sharingapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.sharingapp.data.model.FakePhotoData
import com.dicoding.sharingapp.ui.navigation.Screen
import com.dicoding.sharingapp.ui.theme.SharingAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharingAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SharingAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                SharingApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithDataAndNavigateBack() {
        composeTestRule.onNodeWithTag("PostList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(FakePhotoData.dummyPost[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailsPost.route)
        composeTestRule.onNodeWithText(FakePhotoData.dummyPost[5].name).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_fav).performClick()
        navController.assertCurrentRouteName(Screen.Favorites.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}