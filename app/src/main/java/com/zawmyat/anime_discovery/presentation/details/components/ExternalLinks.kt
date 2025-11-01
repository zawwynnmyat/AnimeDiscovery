package com.zawmyat.anime_discovery.presentation.details.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.R

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun ExternalLinks(
    externalLinks: List<MediaQuery.ExternalLink?>?,
    context: Context,
    onClick : (clickedUrl : String) -> Unit,
    onLongClick : (clickedUrl : String) -> Unit
) {

    Column(
        modifier = Modifier.padding(vertical = 30.dp)
    ) {

        Text(text = "External Links")

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow {
            externalLinks?.let {
                    sources ->
                sources.forEach {
                        link ->
                    link?.let {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(
                                    color = Color(
                                        android.graphics.Color.parseColor(
                                            it.color ?: "#000000"
                                        )
                                    )
                                )
                                .combinedClickable(
                                    onClick = {
                                        onClick(it.url ?: "")
                                    },
                                    onLongClick = {
                                        onLongClick(it.url ?: "")
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            ) {
                                it.icon?.let {
                                        iconUrl ->
                                    AsyncImage(
                                        model = ImageRequest
                                            .Builder(context = context)
                                            .data(iconUrl)
                                            .build(),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(15.dp)
                                            .height(15.dp)
                                    )
                                } ?: Icon(
                                    painter = painterResource(id = R.drawable.insert_link_24),
                                    contentDescription = null,
                                    tint = androidx.compose.ui.graphics.Color.White
                                )


                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = it.language?.let {
                                            language ->
                                        "${it.site} (${it.language})"
                                    } ?: it.site,
                                    color = androidx.compose.ui.graphics.Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Long press to copy the link",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

    }
}