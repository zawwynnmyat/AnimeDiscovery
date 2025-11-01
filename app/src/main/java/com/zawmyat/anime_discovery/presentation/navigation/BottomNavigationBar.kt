package com.zawmyat.anime_discovery.presentation.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf<BottomNavItems>(
        BottomNavItems.Home,
        BottomNavItems.Anime,
        BottomNavItems.Manga,
        BottomNavItems.Account
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarDestination = items.any {
        it.route == currentRoute
    }

    if(bottomBarDestination) {
        BottomAppBar() {
            items.forEach() {
                    item ->
                NavigationBarItem(
                    selected = item.route == currentRoute,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(painter = painterResource(id = item.icon), contentDescription = null)
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }

}