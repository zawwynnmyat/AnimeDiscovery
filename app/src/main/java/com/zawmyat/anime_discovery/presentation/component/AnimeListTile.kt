package com.zawmyat.anime_discovery.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeListTile(
    id: Int,
    title: String,
    favorite: Int,
    coverPhoto: String,
    genres: List<String>,
    startYear: Int,
    averageScore: Int,
    format: String,
    onClick : (clickedId : Int) -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable {
                onClick(id)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxWidth()
                            .height(150.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Spacer(modifier = Modifier.width(120.dp))

                            Column {

                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 16.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = startYear.toString(),
                                        fontSize = 12.sp
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .width(5.dp)
                                            .height(5.dp)
                                            .background(color = MaterialTheme.colorScheme.primary)
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(
                                        text = format,
                                        fontSize = 12.sp
                                    )

                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                // Rating & Favorite
                                Row {
                                    //Rating
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_star_24),
                                            contentDescription = null,
                                            tint = Color(0xfffca103),
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = format(averageScore),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(6.dp))
                                    //Favorite
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_favorite_24),
                                            contentDescription = null,
                                            tint = Color.Red,
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = format(favorite),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp),
                                ) {
                                    genres.take(3).forEach {
                                        Box(
                                            modifier = Modifier.padding(end = 5.dp)
                                        ) {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(color = getGenreColor(it))
                                            ) {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier
                                                        .padding(horizontal = 4.dp),
                                                    color = Color.White,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data(coverPhoto)
                    .build(),
                modifier = Modifier
                    .width(110.dp)
                    .height(165.dp)
                    .padding(start = 12.dp, bottom = 12.dp),
                contentDescription = null
            )

        }
    }

}