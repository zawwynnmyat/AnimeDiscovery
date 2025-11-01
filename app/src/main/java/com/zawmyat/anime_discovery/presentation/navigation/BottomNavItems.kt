package com.zawmyat.anime_discovery.presentation.navigation

import com.zawmyat.anime_discovery.R


sealed class BottomNavItems(val title:String, var icon: Int, var route: String) {
    object Home : BottomNavItems("Home", R.drawable.home, "home")
    object Anime : BottomNavItems("Anime", R.drawable.ic_local_movies_24, "anime")
    object Manga : BottomNavItems("Manga", R.drawable.book, "manga")
    object Account : BottomNavItems("Account", R.drawable.person, "account")
}