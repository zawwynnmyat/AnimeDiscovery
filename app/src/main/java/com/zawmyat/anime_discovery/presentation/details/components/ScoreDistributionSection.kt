package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikephil.charting.data.BarEntry
import com.zawmyat.anime_discovery.MediaQuery

@Composable
fun ScoreDistributionSection(
    scoreDistribution: List<MediaQuery.ScoreDistribution?>,
) {

    var scoreEntries : ArrayList<BarEntry> = ArrayList<BarEntry>()

    scoreDistribution.forEach {
            scoreDis ->
        scoreDis?.let {
            scoreEntries.add(
                BarEntry(
                    it.score?.toFloat() ?: 0f,
                    it.amount?.toFloat() ?: 0f
                )
            )
        }
    }

    if(scoreEntries.isNotEmpty()) {

        Column(
            modifier = Modifier.padding(vertical = 25.dp)
        ) {

            Text(
                text = "Score Distribution",
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            ScoreDistributionBarChart(
                dataEntries = scoreEntries,
                dataLabel = "Score Distribution",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}