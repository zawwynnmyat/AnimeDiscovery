package com.zawmyat.anime_discovery.presentation.trending.component

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
fun TrendingAppBar(
    title: String,
    isList: Boolean,
    onNavBackClick: () -> Unit,
    onStyleChange : () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavBackClick()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_ios_new_24),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onStyleChange() }
            ) {
                if(isList) {
                    Icon(
                        painter = painterResource(id = R.drawable.grid_view),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.list_bulleted),
                        contentDescription = null
                    )
                }

            }
        }
    )
}