package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Information(
    titleRomanji : String,
    titleEnglish: String,
    titleNative : String,
    synonyms : List<String>,
    format: String,
    status: String,
    countryOfOrigin: String,
    startDay: Int,
    startMonth: Int,
    startYear: Int,
    endDay: Int,
    endMonth: Int,
    endYear: Int,
    episodes: Int
) {
    val monthMap = mapOf(
        1 to "Jaunary",
        2 to "February",
        3 to "March",
        4 to "April",
        5 to "May",
        6 to "June",
        7 to "July",
        8 to "August",
        9 to "September",
        10 to "October",
        11 to "November",
        12 to "December"
    )

    Column(
        modifier = Modifier.padding(vertical = 15.dp)
    ) {

        Text(
            text = "Information",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        InfoTile(
            title = "Romanji",
            content = titleRomanji
        )

        InfoTile(
            title = "English",
            content = titleEnglish
        )

        InfoTile(
            title = "Native",
            content = titleNative
        )

        InfoTile(
            title = "Synonyms",
            content = synonyms.joinToString(separator = "\n")
        )

        InfoTile(
            title = "Format",
            content = format
        )

        InfoTile(
            title = "Episodes",
            content = if(episodes == 0) "-" else episodes.toString()
        )
        InfoTile(
            title = "Status",
            content = status
        )

        InfoTile(
            title = "Country of Origin",
            content = countryOfOrigin
        )


        InfoTile(
            title = "Start Date",
            content = "${if(startDay == 0) "." else startDay} " +
                    "${if(startMonth == 0) "." else monthMap.get(startMonth)} " +
                    "${if(startYear == 0) "." else startYear}"
        )

        InfoTile(
            title = "End Date",
            content = "${if(endDay == 0) "." else endDay} " +
                    "${if(endMonth == 0) "." else monthMap.get(endMonth)} " +
                    "${if(endYear == 0) "." else endYear}"
        )

    }
}