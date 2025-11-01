package com.zawmyat.anime_discovery.presentation.staffs.component

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.StaffDetailQuery
import com.zawmyat.anime_discovery.presentation.details.components.TitleWithSeeAll

@Composable
fun CharactersForStaff(
    characters: StaffDetailQuery.Characters?,
    context: Context,
    onCharacterClick: (id: Int) -> Unit,
    onSeeAllClick : () -> Unit
) {

    Column {
        characters?.let {
            TitleWithSeeAll(
                title = "Characters",
                onSeeAllClick = {
                    onSeeAllClick()
                }
            )
        }

        characters?.let {
            Spacer(modifier = Modifier.height(10.dp))
        }

        characters?.let {
            LazyRow {
                items(it.edges?.size ?: 0) {
                        index ->
                    Column(
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .clickable {
                                onCharacterClick(it.edges?.get(index)?.node?.id ?: 0)
                            }
                    ) {

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(80.dp)
                                .height(80.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest
                                    .Builder(context)
                                    .data(it.edges?.get(index)?.node?.image?.large ?: "")
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(80.dp)
                            )
                        }


                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = it.edges?.get(index)?.node?.name?.full ?: "",
                            fontSize = 12.sp,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(80.dp)
                        )

                    }
                }
            }
        }
    }
}