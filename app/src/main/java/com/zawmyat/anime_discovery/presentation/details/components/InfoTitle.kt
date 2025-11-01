package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun InfoTile(
    title: String,
    content: String
) {
    Row {
        Text(
            text = title,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = content,
            modifier = Modifier.weight(1f),
            fontSize = 13.sp
        )

    }
}