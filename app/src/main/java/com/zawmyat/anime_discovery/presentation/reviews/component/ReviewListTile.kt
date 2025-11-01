package com.zawmyat.anime_discovery.presentation.reviews.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.data.utils.getDateString

@Composable
fun ReviewListTile(
    id: Int,
    bannerImage: String,
    mediaTitleRomanji: String,
    mediaFormat: String,
    reviewerName: String,
    reviewerAvatar: String,
    summary: String,
    upVote: Int,
    totalVote: Int,
    score: Int,
    timeStamp: Int,
    onClick : (clickedId : Int) -> Unit
) {

    val context = LocalContext.current

    val downVote = totalVote - upVote

    val createdTime = getDateString(timeStamp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable {
                onClick(id)
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data(bannerImage)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(0.5f),
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface.copy(0.5f),
                            MaterialTheme.colorScheme.surface,
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.Black.copy(0.3f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row() {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .height(55.dp)
                                .width(55.dp)
                                .background(color = Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .height(52.dp)
                                    .width(52.dp)
                            ) {
                                AsyncImage(
                                    model = ImageRequest
                                        .Builder(context = context)
                                        .data(reviewerAvatar)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .height(80.dp)
                                        .height(80.dp)
                                )
                            }
                        }


                        Spacer(modifier = Modifier.width(8.dp))

                        Column {

                            Text(
                                text = "Review of ${mediaTitleRomanji} (${mediaFormat}) by ${reviewerName}",
                                fontSize = 14.sp,
                                lineHeight = 15.sp,
                                color = Color.White,
                                maxLines = 3,
                            )

                            Text(
                                text = createdTime,
                                fontSize = 12.sp,
                                color = Color.White,
                            )


                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = summary,
                        fontSize = 12.sp,
                        lineHeight = 15.sp,
                        color = Color.White,
                        maxLines = 5,
                    )
                }


                //Upvote, downvote and score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.thumb_up_24),
                            contentDescription = null,
                            tint = Color.Green,
                            modifier = Modifier.size(17.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = format(upVote),
                            color = Color.White,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.thumb_down_alt_24),
                            contentDescription = null,
                            tint = Color(0xffdb2e0b),
                            modifier = Modifier.size(17.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = format(downVote),
                            color = Color.White,
                            fontSize = 12.sp
                        )

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_24),
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(17.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = format(score),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

    }
}