package com.zawmyat.anime_discovery.presentation.details.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StaffGridCell(
    id: Int,
    coverPhoto: String,
    name: String,
    role: String,
    context: Context,
    onClick : (staffId : Int) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onClick(id)
        }
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = context)
                .data(coverPhoto)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .fillMaxWidth()
                .height(165.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )


        Text(
            text = role,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )


    }
}