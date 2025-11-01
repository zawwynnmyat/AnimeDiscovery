package com.zawmyat.anime_discovery.presentation.navigation

sealed class DetailNavItems(var route: String) {

    object AllTrendingAnimePage : DetailNavItems(route = "all_trending_anime")
    object AllTrendingMangaPage : DetailNavItems(route = "all_trending_manga")
    object AnimeDetailPage: DetailNavItems(route = "anime_details")
    object ReviewsPage : DetailNavItems(route = "reviews")
    object CharactersPage : DetailNavItems(route = "characters")
    object StudiosPage : DetailNavItems(route = "studios")
    object StaffsPage : DetailNavItems(route = "staffs")
    object AnimeTop100 : DetailNavItems(route = "anime_top100")
    object AnimeTopPopular : DetailNavItems(route = "anime_top_popular")
    object AnimeUpcoming : DetailNavItems(route = "anime_upcoming")
    object SpringAnime : DetailNavItems(route = "spring")
    object SummerAnime : DetailNavItems(route = "summer")
    object FallAnime : DetailNavItems(route = "fall")
    object WinterAnime : DetailNavItems(route = "winter")
    object MangaTop100 : DetailNavItems(route = "manga_top100")
    object MangaTopPopular : DetailNavItems(route = "manga_top_popular")
    object MangaUpcoming : DetailNavItems(route = "manga_upcoming")
    object MangaPublishing : DetailNavItems(route = "manga_publishing")
    object SettingPage : DetailNavItems(route = "settings")

    object ReviewDetailsPage : DetailNavItems(route = "review_details")
    object CharacterDetailsPage : DetailNavItems(route = "character_details")
    object StudioDetailsPage : DetailNavItems(route = "studio_details")
    object StaffDetailsPage : DetailNavItems(route = "staff_details")

    object SeasonalPage : DetailNavItems(route = "seasonal")
    object StatusFilteredMediaPage : DetailNavItems(route = "status_filtered")
    object SortedMediaPage : DetailNavItems(route = "sorted")
    object TagMatchedMediaPage : DetailNavItems(route = "tag_matched")

    object MediaStaffListPage : DetailNavItems(route = "media_staff_list")
    object MediaCharacterListPage : DetailNavItems(route = "media_character_list")
    object StaffMediasListPage : DetailNavItems(route = "staff_medias_list")
    object StaffCharacterListPage : DetailNavItems(route = "staff_characters_list")
    object CharacterMediaListPage : DetailNavItems(route = "character_media_list")

    object PhotoPage : DetailNavItems(route = "photo_view")

    object SearchMediaPage : DetailNavItems(route = "search_medias")
    object SearchCharacters : DetailNavItems(route = "search_characters")
    object SearchStaffs : DetailNavItems(route = "search_staffs")

}