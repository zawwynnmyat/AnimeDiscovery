package com.zawmyat.anime_discovery.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zawmyat.anime_discovery.presentation.base.BaseScreen
import com.zawmyat.anime_discovery.presentation.details.AnimeDetails
import com.zawmyat.anime_discovery.presentation.onboarding.OnboardingProcessScreen

@Composable
fun RootNavigationGraph(
    navController : NavHostController,
    accessToken: String,
    initialRoute: String
) {

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = initialRoute
    ) {

        composable(Graph.BOTTOM_NAV) {
            BaseScreen(accessToken = accessToken)
        }

        composable(Graph.ONBOARDING) {
            OnboardingProcessScreen(navController = navController)
        }

    }
}