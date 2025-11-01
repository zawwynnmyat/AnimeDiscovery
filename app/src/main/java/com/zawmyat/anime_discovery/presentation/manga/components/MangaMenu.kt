package com.zawmyat.anime_discovery.presentation.manga.components

import com.zawmyat.anime_discovery.presentation.anime.component.AnimeMenuOperation

data class MangaMenu(
    val icon : Int,
    val title : String,
    val operation: MangaMenuOperation
)