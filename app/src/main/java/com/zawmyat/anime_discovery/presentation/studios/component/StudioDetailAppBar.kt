package com.zawmyat.anime_discovery.presentation.studios.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.zawmyat.anime_discovery.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudioDetailAppBar(
    studioName : String,
    onNavIconClick : () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = studioName)
        },
        navigationIcon = {
            IconButton(
                onClick = { onNavIconClick() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_ios_new_24),
                    contentDescription = null
                )
            }
        }
    )
}