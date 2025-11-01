package com.zawmyat.anime_discovery.presentation.details.components

import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun StatusDistributionPieChart(
    statusDataEntries: ArrayList<PieEntry>,
    dataLabel: String,
    modifier: Modifier,
) {

    AndroidView(
        modifier = modifier,
        factory = {
            context ->

            val chart = PieChart(context)

            val dataSet = PieDataSet(statusDataEntries, dataLabel).apply {
                setColors(
                    Color(0xff0eb334).toArgb(),
                    Color(0xff0c98cf).toArgb(),
                    Color(0xffdb236d).toArgb(),
                    Color(0xffad48d9).toArgb(),
                    Color(0xffdb7a0b).toArgb()
                )

            }

            chart.data = PieData(dataSet)
            chart.setDrawSliceText(false)
            chart.description.isEnabled = false

            chart.description.textColor = Color(0xff0eb334).toArgb()
            chart.setEntryLabelColor(Color(0xff0eb334).toArgb())
            chart.setNoDataTextColor(Color(0xff0eb334).toArgb())

            chart

        }
    )
}