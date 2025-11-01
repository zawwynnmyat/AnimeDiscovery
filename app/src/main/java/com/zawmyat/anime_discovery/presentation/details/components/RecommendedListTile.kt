package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun RecommendedListTile(
    id: Int,
    coverPhoto: String,
    title: String,
    averageScore: Int,
    format: String,
    popularity: Int,
    favorites: Int,
    startYear: Int,
    genres: List<String>,
    modifier: Modifier,
    onRecommendedClicked : (clickedId : Int) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .clickable {
                onRecommendedClicked(id)
            }
    ) {

        Row {
            Box {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = context)
                        .data(coverPhoto)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .width(110.dp)
                        .height(165.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(165.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(color = Color.Black.copy(alpha = 0.7f))
                    ) {
                        Text(
                            text = format,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }


            }


            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.weight(3f)
                    ) {
                        Text(
                            text = title,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1.5f),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row {
                            Text(
                                text = averageScore.toString(),
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.width(3.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.baseline_bar_chart_24),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Row {
                            Text(
                                text = popularity.toString(),
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.width(3.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.people_alt_24),
                                contentDescription = null,
                            )
                        }

                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite_24),
                        contentDescription = null,
                        tint = Color(0xffdb2e0b),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = format(favorites),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_month_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = startYear.toString(),
                        fontSize = 12.sp
                    )

                }


                GenreDetails(
                    genres = genres, limit = 2
                )


            }

        }

    }
}