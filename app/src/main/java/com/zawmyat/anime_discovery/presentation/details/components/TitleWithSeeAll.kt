package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TitleWithSeeAll(
    title: String,
    onSeeAllClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title
        )

        TextButton(
            onClick = {
                onSeeAllClick()
            }
        ) {
            Text(
                text = "See All",
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}