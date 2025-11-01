package com.zawmyat.anime_discovery.presentation.characters.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun CharacterListTile(
    id: Int,
    name: String,
    photo: String,
    favorites: Int,
    gender: String,
    age: String,
    count: Int,
    onClick: (characterId : Int) -> Unit
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clickable {
                onClick(id)
            },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(165.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context = context)
                            .data(photo)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.width(110.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        //Favorites
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite_24),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xffdb2e0b)
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = format(favorites),
                                fontSize = 14.sp
                            )

                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        //Gender
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = when(gender) {
                                    "Male" -> painterResource(id = R.drawable.male)
                                    "Female" -> painterResource(id = R.drawable.female)
                                    else -> painterResource(id = R.drawable.transgender)
                                },
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "Gender : ${gender}",
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        //Age
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.catching_pokemon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = "Age : ${age}",
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                        }

                    }
                }
            }

            Box(modifier = Modifier.padding(all = 12.dp)) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(30.dp)
                        .height(30.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = format(count + 1),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 15.sp
                    )
                }
            }

        }

    }
}