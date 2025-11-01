package com.zawmyat.anime_discovery.presentation.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zawmyat.anime_discovery.presentation.navigation.BottomNavigationBar
import com.zawmyat.anime_discovery.presentation.navigation.BottomNavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    navController: NavHostController = rememberNavController(),
    accessToken: String
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
        ){
            BottomNavigationGraph(
                navController = navController,
                accessToken = accessToken
            )
        }
    }
}