package com.zawmyat.anime_discovery.presentation.onboarding

import androidx.annotation.DrawableRes
import com.zawmyat.anime_discovery.R

sealed class OnBoardingPage(
    val image: Int,
    val title: String,
    val description: String
) {

    object FirstPage : OnBoardingPage(
        image = R.raw.onboarding0,
        title = "Explore Anime",
        description = "You can explore various anime provided the AniList."
    )

    object SecondPage : OnBoardingPage(
        image = R.raw.onboarding1,
        title = "Explore Manga",
        description = "You can explore various manga provided the AniList."
    )

    object ThirdPage : OnBoardingPage(
        image = R.raw.onboarding2,
        title = "Manage with AniList",
        description = "Anime Discovery is an unofficial client for AniList, a place where you can track, share, discovery, and experience Anime & Manga."
    )
}