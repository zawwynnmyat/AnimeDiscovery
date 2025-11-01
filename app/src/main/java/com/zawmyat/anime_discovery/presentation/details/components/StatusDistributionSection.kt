package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.mikephil.charting.data.PieEntry
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.data.utils.formatFloat

@Composable
fun StatusDistributionSection(
    statusDistribution : List<MediaQuery.StatusDistribution?>,
) {

    var statusDatas : ArrayList<PieEntry> = ArrayList<PieEntry>()

    statusDistribution.forEach {
            statusDist ->
        statusDist?.let {
            statusDatas.add(
                PieEntry(
                    it.amount?.toFloat() ?: 0f,
                    it.status?.name ?: "N/A"
                )
            )
        }
    }

    val lables = listOf(
        "Watching",
        "Planning",
        "Completed",
        "Dropped",
        "Paused"
    )

    val colors = listOf(
        Color(0xff0eb334),
        Color(0xff0c98cf),
        Color(0xffdb236d),
        Color(0xffad48d9),
        Color(0xffdb7a0b)
    )



    if(statusDatas.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(vertical = 25.dp)
        ) {

            Text(
                text = "Status Distribution",
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            StatusDistributionPieChart(
                statusDataEntries = statusDatas,
                dataLabel = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            if(statusDatas.size == lables.size) {

                Column {

                    for(i in 0..(statusDatas.size-1)) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .height(20.dp)
                                    .width(20.dp)
                                    .background(color = colors[i])
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = lables[i],
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = formatFloat(statusDatas[i].value),
                                fontSize = 14.sp
                            )
                        }
                    }

                }

            }

        }
    }
}