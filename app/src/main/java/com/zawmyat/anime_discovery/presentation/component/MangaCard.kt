package com.zawmyat.anime_discovery.presentation.component

import android.text.SpannableStringBuilder
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.TrendingMangaHomeQuery
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.data.utils.toAnnotatedString

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MangaCard(
    title: String,
    staffs: List<TrendingMangaHomeQuery.Edge>,
    favorite: Int,
    coverPhoto: String,
    bannerImage: String,
    description: String,
    genres: List<String>,
    startYear: Int,
    averageScore: Int,
    onClick : () -> Unit
) {

    val context = LocalContext.current

    val spannableString = SpannableStringBuilder(description).toString()
    val spanned = HtmlCompat.fromHtml(spannableString, HtmlCompat.FROM_HTML_MODE_COMPACT)

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .width(325.dp)
            .height(350.dp)
            .padding(end = 12.dp)
            .clickable {
                 onClick()
            },
        ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(bannerImage)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                        MaterialTheme.colorScheme.surface,
                                    )
                                )
                            ),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 12.dp)

                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(coverPhoto)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(90.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {

                                Text(
                                    text = title,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = if(staffs.isEmpty()) {
                                        "-"
                                    } else {
                                        staffs.first().node?.name?.full ?: "-"
                                    },
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 15.sp
                                )

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

                                    Spacer(modifier = Modifier.width(6.dp))

                                    //Year
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_calendar_month_24),
                                            contentDescription = null,
                                            tint = Color(0xff0eb334),
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = startYear.toString(),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                }

                            }
                        }
                    }
                }
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                genres.take(3).forEach {
                    Box(
                        modifier = Modifier.padding(end = 8.dp)
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

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = spanned.toAnnotatedString(),
                maxLines = 5,
                fontSize = 13.sp,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}