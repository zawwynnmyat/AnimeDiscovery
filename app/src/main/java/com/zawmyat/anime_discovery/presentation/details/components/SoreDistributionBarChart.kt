package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry


@Composable
fun ScoreDistributionBarChart(
    dataEntries: ArrayList<BarEntry>,
    dataLabel: String,
    modifier: Modifier
) {


    AndroidView(
        modifier = modifier,
        factory = {
            context ->
            val chart = BarChart(context)

            val dataset = BarDataSet(dataEntries, dataLabel).apply {
                color = Color.Green.toArgb()
                setGradientColor(Color.Green.toArgb(), Color.Red.toArgb())

            }

            chart.setTouchEnabled(false)
            chart.isScaleXEnabled = false
            chart.isScaleYEnabled = false
            chart.isDragEnabled = false

            chart.xAxis.setLabelCount(10)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM

            chart.xAxis.setDrawGridLines(false)
            chart.axisLeft.setDrawGridLines(false)
            chart.axisRight.setDrawGridLines(false)
            chart.setDrawGridBackground(false)

            chart.data = BarData(dataset)
            chart.data.barWidth = 1f
            chart.description.isEnabled = false
            chart.xAxis.textColor = Color(0xff0eb334).toArgb()
            chart.axisLeft.textColor = Color(0xff0eb334).toArgb()
            chart.description.textColor = Color(0xff0eb334).toArgb()
            chart.axisRight.textColor = Color(0xff0eb334).toArgb()
            chart.data.setValueTextColor(Color(0xff0eb334).toArgb())
            //chart.xAxis.textSize = 18f

            chart.invalidate()

            chart
        }
    )
}
