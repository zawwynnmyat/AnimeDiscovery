package com.zawmyat.anime_discovery.presentation.staffs.component

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
fun StaffsAppBar(
    onBack : () -> Unit,
    onSearchClick : () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Staffs")
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_ios_new_24),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = null
                )
            }
        }
    )
}