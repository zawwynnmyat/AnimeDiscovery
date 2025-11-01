package com.zawmyat.anime_discovery.presentation.component

import androidx.compose.ui.graphics.Color

//"Action",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      "",
//      ""

fun getGenreColor(genre: String) : Color {
    return when(genre) {
        "Adventure" -> Color(0xffdb2e0b)
        "Action" -> Color(0xffdb7a0b)
        "Comedy" -> Color(0xff0eb334)
        "Drama" -> Color(0xff0c98cf)
        "Ecchi" -> Color(0xffab44db)
        "Fantasy" -> Color(0xffdb236d)
        "Hentai" -> Color(0xff0da18f)
        "Horror" -> Color(0xff1085c9)
        "Mahou Shoujo" -> Color(0xffad48d9)
        "Mecha" -> Color(0xffe611ed)
        "Music" -> Color(0xff0cb08f)
        "Mystery" -> Color(0xffc93712)
        "Psychological" -> Color(0xffe37971)
        "Romance" -> Color(0xfff2962c)
        "Sci-Fi" -> Color(0xffdec237)
        "Slice of Life" -> Color(0xffc0cf1d)
        "Sports" -> Color(0xff8ecc3d)
        "Supernatural" -> Color(0xff47bf7f)
        "Thriller" -> Color(0xff24adbf)
        else -> Color(0xff0eb334)
    }
}