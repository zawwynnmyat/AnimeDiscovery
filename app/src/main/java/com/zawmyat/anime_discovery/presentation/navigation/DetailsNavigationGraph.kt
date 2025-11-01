package com.zawmyat.anime_discovery.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.zawmyat.anime_discovery.presentation.anime.AllSeasonalMedia
import com.zawmyat.anime_discovery.presentation.characters.AllCharacterMedia
import com.zawmyat.anime_discovery.presentation.characters.CharacterDetails
import com.zawmyat.anime_discovery.presentation.characters.Characters
import com.zawmyat.anime_discovery.presentation.details.AllMediaCharacter
import com.zawmyat.anime_discovery.presentation.details.AllMediaStaff
import com.zawmyat.anime_discovery.presentation.details.AnimeDetails
import com.zawmyat.anime_discovery.presentation.details.TagMatchedMediaView
import com.zawmyat.anime_discovery.presentation.manga.SortedMediasView
import com.zawmyat.anime_discovery.presentation.manga.StatusFilteredMediaView
import com.zawmyat.anime_discovery.presentation.photo.PhotoView
import com.zawmyat.anime_discovery.presentation.reviews.AllReviews
import com.zawmyat.anime_discovery.presentation.reviews.ReviewDetails
import com.zawmyat.anime_discovery.presentation.search.SearchCharactersPage
import com.zawmyat.anime_discovery.presentation.search.SearchMediaPage
import com.zawmyat.anime_discovery.presentation.search.SearchStaffsPage
import com.zawmyat.anime_discovery.presentation.trending.AllTrendingAnime
import com.zawmyat.anime_discovery.presentation.setting.SettingPage
import com.zawmyat.anime_discovery.presentation.staffs.AllStaffCharacters
import com.zawmyat.anime_discovery.presentation.staffs.AllStaffMedias
import com.zawmyat.anime_discovery.presentation.staffs.StaffDetails
import com.zawmyat.anime_discovery.presentation.staffs.Staffs
import com.zawmyat.anime_discovery.presentation.studios.StudioDetails
import com.zawmyat.anime_discovery.presentation.studios.Studios
import com.zawmyat.anime_discovery.presentation.trending.AllTrendingManga
import com.zawmyat.anime_discovery.type.MediaType

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.DetailsNavigationGraph(
    navController: NavHostController,
) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailNavItems.AnimeDetailPage.route,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {


        composable(
            DetailNavItems.AnimeDetailPage.route + "/id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            AnimeDetails(
                navController = navController,
                id = id
            )
        }

        composable(
            route = DetailNavItems.StudioDetailsPage.route + "/id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            StudioDetails(
                navController = navController,
                id = id
            )
        }

        composable(
            route = DetailNavItems.StaffDetailsPage.route + "/id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            StaffDetails(
                navController = navController,
                id = id
            )
        }


        composable(DetailNavItems.AllTrendingAnimePage.route) {
            AllTrendingAnime(navController = navController)
        }

        composable(DetailNavItems.AllTrendingMangaPage.route) {
            AllTrendingManga(navController = navController)
        }

        composable(DetailNavItems.ReviewsPage.route) {
            AllReviews(navController = navController)
        }

        composable(DetailNavItems.CharactersPage.route) {
            Characters(navController = navController)
        }

        composable(DetailNavItems.StudiosPage.route) {
            Studios(navController = navController)
        }

        composable(DetailNavItems.StaffsPage.route) {
            Staffs(navController = navController)
        }

        composable(DetailNavItems.SettingPage.route) {
            SettingPage(navController = navController)
        }

        composable(
            route = DetailNavItems.ReviewDetailsPage.route + "/review_id={review_id}",
            arguments = listOf(
                navArgument(name = "review_id") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->

            val rId = backStackEntry.arguments?.getInt("review_id")

            Log.i("received_id", rId.toString())

            rId?.let {
                id ->
                ReviewDetails(navController = navController, reviewId = id)
            }
        }

        composable(
            route = DetailNavItems.SeasonalPage.route + "/season={season}/mediaType={mediaType}",
            arguments = listOf(
                navArgument(name = "season") {
                    type = NavType.StringType
                },
                navArgument(name = "mediaType") {
                    type = NavType.StringType
                }
            )
        ) {
            backStackEntry ->

            val season = backStackEntry.arguments?.getString("season")
            val type = backStackEntry.arguments?.getString("mediaType")

            if(season != null && type != null) {

                AllSeasonalMedia(
                    season = season,
                    type = type,
                    navController = navController
                )
            }
        }

        composable(
            route = DetailNavItems.StatusFilteredMediaPage.route + "/status={status}/mediaType={mediaType}",
            arguments = listOf(
                navArgument(name = "status") {
                    type = NavType.StringType
                },
                navArgument(name = "mediaType") {
                    type = NavType.StringType
                }
            )
        ) {
            backStackEntry ->

            val status = backStackEntry.arguments?.getString("status")
            val type = backStackEntry.arguments?.getString("mediaType")

            if(status != null && type != null) {
                StatusFilteredMediaView(
                    status = status,
                    type = type,
                    navController = navController
                )
            }
        }

        composable(
            route = DetailNavItems.SortedMediaPage.route + "/sort={sort}/mediaType={mediaType}",
            arguments = listOf(
                navArgument(name = "sort") {
                    type = NavType.StringType
                },
                navArgument(name = "mediaType") {
                    type = NavType.StringType
                }
            )
        ) {
                backStackEntry ->

            val sort = backStackEntry.arguments?.getString("sort")
            val type = backStackEntry.arguments?.getString("mediaType")

            if(sort != null && type != null) {
                SortedMediasView(
                    sort = sort,
                    type = type,
                    navController = navController
                )
            }
        }


        composable(
            route = DetailNavItems.TagMatchedMediaPage.route + "/tag={tag}",
            arguments = listOf(
                navArgument(name = "tag") {
                    type = NavType.StringType
                },
            )
        ) {
                backStackEntry ->

            val tag = backStackEntry.arguments?.getString("tag")

            tag?.let {
                TagMatchedMediaView(
                    tag = it,
                    navController = navController
                )
            }
        }

        composable(
            route = DetailNavItems.MediaStaffListPage.route + "/mediaId={mediaId}",
            arguments = listOf(
                navArgument(name = "mediaId") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->

            val mediaId = backStackEntry.arguments?.getInt("mediaId")

            mediaId?.let {
                AllMediaStaff(navController = navController, mediaId = mediaId)
            }
        }


        composable(
            route = DetailNavItems.MediaCharacterListPage.route + "/mediaId={mediaId}",
            arguments = listOf(
                navArgument(name = "mediaId") {
                    type = NavType.IntType
                }
            )
        ) {
                backStackEntry ->

            val mediaId = backStackEntry.arguments?.getInt("mediaId")

            mediaId?.let {
               AllMediaCharacter(navController = navController, mediaId = mediaId)
            }
        }


        composable(
            route = DetailNavItems.StaffMediasListPage.route + "/staffId={staffId}",
            arguments = listOf(
                navArgument(name = "staffId") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->

            val staffId = backStackEntry.arguments?.getInt("staffId")

            staffId?.let {
                AllStaffMedias(
                    staffId = staffId,
                    navController = navController
                )
            }
        }

        composable(
            route = DetailNavItems.StaffCharacterListPage.route + "/staffId={staffId}",
            arguments = listOf(
                navArgument(name = "staffId") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->
            val staffId = backStackEntry.arguments?.getInt("staffId")

            staffId?.let {
                AllStaffCharacters(
                    staffId = it,
                    navController = navController
                )
            }
        }

        composable(
            route = DetailNavItems.CharacterMediaListPage.route + "/characterId={characterId}",
            arguments = listOf(
                navArgument(name = "characterId") {
                    type = NavType.IntType
                }
            )
        ) {
            backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId")

            characterId?.let {
                AllCharacterMedia(navController = navController, characterId = it)
            }
        }

        composable(
            route = DetailNavItems.CharacterDetailsPage.route + "/id={id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
                backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            CharacterDetails(
                navController = navController,
                id = id,
            )
        }

        composable(
            route = DetailNavItems.PhotoPage.route + "/photo_url={photo_url}/name={name}",
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                },
                navArgument(name = "photo_url") {
                    type = NavType.StringType
                }
            )
        ) {
            backStackEntry ->
            val photoUrl = backStackEntry.arguments?.getString("photo_url")
            val name = backStackEntry.arguments?.getString("name")

            Log.i("photoUrl", photoUrl.toString())

            if(name != null && photoUrl != null) {
                PhotoView(
                    photoUrl = photoUrl,
                    name = name,
                    navController = navController
                )
            }

        }

        composable(
            route = DetailNavItems.SearchMediaPage.route + "/mediaType={mediaType}",
            arguments = listOf(
                navArgument("mediaType") {
                    type = NavType.StringType
                }
            )
        ) {
            backStackEntry ->

            val mediaType = backStackEntry.arguments?.getString("mediaType")

            var mediaTypeToProceed = MediaType.UNKNOWN__

            mediaType?.let {
                when(it) {
                    "ANIME" -> mediaTypeToProceed = MediaType.ANIME
                    "MANGA" -> mediaTypeToProceed = MediaType.MANGA
                    else -> mediaTypeToProceed = MediaType.UNKNOWN__
                }

                SearchMediaPage(
                    mediaType = mediaTypeToProceed,
                    navController = navController
                )
            }
        }

        composable(route = DetailNavItems.SearchCharacters.route) {
            SearchCharactersPage(navController = navController)
        }

        composable(route = DetailNavItems.SearchStaffs.route) {
            SearchStaffsPage(navController = navController)
        }


    }
}