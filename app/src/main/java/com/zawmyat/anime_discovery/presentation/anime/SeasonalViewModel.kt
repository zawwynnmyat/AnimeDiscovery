package com.zawmyat.anime_discovery.presentation.anime

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.SeasonalMediaQuery
import com.zawmyat.anime_discovery.data.repository.MainRepositoryImpl
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaSeason
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.LocalDate

class SeasonalViewModel(
    private val mainRepository: MainRepository,
    private val season: String,
    private val type: String,
    private val currentYear: Int,
) : ViewModel() {

    var seasonalMedias : Flow<PagingData<SeasonalMediaQuery.Medium>> = emptyFlow()

    init {

        val mediaSeason = when(season) {
            "SPRING" -> MediaSeason.SPRING
            "SUMMER" -> MediaSeason.SUMMER
            "FALL" -> MediaSeason.FALL
            else -> MediaSeason.SPRING
        }

        val mediaType = when(type) {
            "ANIME" -> MediaType.ANIME
            "MANGA" -> MediaType.MANGA
            else -> MediaType.ANIME
        }

        seasonalMedias = mainRepository.getAllPaginatedSeasonalMedia(mediaSeason, mediaType, currentYear).cachedIn(viewModelScope)
    }


}