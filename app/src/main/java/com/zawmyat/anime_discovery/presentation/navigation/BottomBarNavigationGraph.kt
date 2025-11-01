package com.zawmyat.anime_discovery.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zawmyat.anime_discovery.presentation.account.Account
import com.zawmyat.anime_discovery.presentation.anime.Anime
import com.zawmyat.anime_discovery.presentation.home.Home
import com.zawmyat.anime_discovery.presentation.manga.Manga


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    accessToken: String
) {

    NavHost(
        navController = navController,
        route = Graph.BOTTOM_NAV,
        startDestination = BottomNavItems.Home.route,
        exitTransition = {
            ExitTransition.None
        },
        enterTransition = {
            EnterTransition.None
        }
    ) {
        composable(BottomNavItems.Home.route) {
            Home(navController = navController)
        }
        composable(BottomNavItems.Anime.route) {
            Anime(
                navController = navController,
                accessToken = accessToken
            )
        }
        composable(BottomNavItems.Manga.route) {
            Manga(
                navController = navController,
                accessToken = accessToken
            )
        }

        composable(BottomNavItems.Account.route) {
            Account(
                navController = navController,
                accessToken = accessToken
            )
        }

        DetailsNavigationGraph(
            navController = navController,
        )
    }


}