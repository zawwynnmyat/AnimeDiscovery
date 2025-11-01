package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zawmyat.anime_discovery.MediaQuery

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TagCell(
    tag : MediaQuery.Tag?,
    onTagClick : (tagName : String) -> Unit
) {

    var showDetails by remember {
        mutableStateOf(false)
    }

    if(showDetails) {
        AlertDialog(
            onDismissRequest = {
                showDetails = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDetails = false
                    }
                ) {
                    Text(text = "Close")
                }
            },
            title = {
                Text(text = tag?.name ?: "")
            },
            text = {
                Column {
                    Text(text = tag?.category ?: "")

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(text = tag?.description ?: "")
                }
            }
        )
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = if (tag?.isMediaSpoiler ?: false) {
                    Color(0xffdb7a0b)
                } else {
                    MaterialTheme.colorScheme.primaryContainer
                }
            )
            .combinedClickable(
                onClick = {
                    onTagClick(tag?.name ?: "")
                },
                onLongClick = {
                    showDetails = true
                }
            )
    ) {

        Row {
            Text(
                text = tag?.name ?: "N/A",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 8.dp, end = 5.dp),
                fontSize = 10.sp
            )

            Box(
                modifier = Modifier.background(
                    color = if(tag?.isMediaSpoiler ?: false) {
                        Color(0xffdb2e0b)
                    } else {
                        Color(0xff12a6a1)
                    }
                )
            ) {
                Text(
                    text = (tag?.rank ?: "N/A").toString() + "%",
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }

    }
}