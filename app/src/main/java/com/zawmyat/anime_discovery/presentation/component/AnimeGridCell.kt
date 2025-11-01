package com.zawmyat.anime_discovery.presentation.component

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun AnimeGridCell(
    id: Int,
    format: String,
    title: String,
    coverPhoto: String,
    year: Int,
    averageScore: Int,
    onClick : (clickedId : Int) -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.clickable {
            onClick(id)
        }
    ) {
        Box() {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data(coverPhoto)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(165.dp),
                contentScale = ContentScale.Crop
            )

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

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 13.sp
        )

        Row {
            Text(
                text = year.toString(),
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.width(5.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_star_24),
                contentDescription = null,
                tint = Color(0xfffca103),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = averageScore.toString(),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }
}