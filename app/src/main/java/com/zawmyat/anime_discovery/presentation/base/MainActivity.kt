package com.zawmyat.anime_discovery.presentation.base

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.AnimeDiscoveryTheme
import com.zawmyat.anime_discovery.data.auth.Constants
import com.zawmyat.anime_discovery.data.auth.Token
import com.zawmyat.anime_discovery.presentation.account.AccountViewModel
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.navigation.BottomNavigationBar
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.navigation.Graph
import com.zawmyat.anime_discovery.presentation.navigation.RootNavigationGraph
import com.zawmyat.anime_discovery.presentation.setting.SettingViewModel
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutName
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {

    val accountViewModel : AccountViewModel by inject<AccountViewModel>()
    val settingViewModel : SettingViewModel by inject<SettingViewModel>()
    val shortcutViewModel: ShortcutViewModel by viewModels<ShortcutViewModel>()

    val token: Token by inject<Token>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        shortcutViewModel.handleIntent(intent)

        installSplashScreen().setKeepOnScreenCondition {
            !settingViewModel.isLoadedTheme.value ||
                    !settingViewModel.isLoadedOnboardStatus.value ||
                    !settingViewModel.isLoadedAccessToken.value
        }

        setContent {

            val themeName = settingViewModel.currentTheme.collectAsStateWithLifecycle()

            AnimeDiscoveryTheme(
                themeName = themeName.value
            ) {

                val accessToken by settingViewModel.accessToken.collectAsState()
                token.accessToken = accessToken

                val navController = rememberNavController()

                RootNavigationGraph(
                    navController = navController,
                    accessToken = accessToken,
                    initialRoute = settingViewModel.initialRoute.value
                )

            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        shortcutViewModel.handleIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data

        uri?.let {
            safeUri ->
            if(safeUri.toString().startsWith(Constants.REDIRECT_URL)) {

                val authenticationCode = safeUri.getQueryParameter("code")!!

                accountViewModel.makeAuthRequest(
                    clientId = Constants.CLIENT_ID,
                    clientSecret = Constants.CLIENT_SECRET,
                    code = authenticationCode
                )
            }
        }
    }

}


@Composable
fun MangaListTile() {

}


@Composable
fun AnimeGridCell() {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .width(140.dp)
            .height(220.dp)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data("https://s4.anilist.co/file/anilistcdn/media/manga/cover/large/bx30013-ulXvn0lzWvsz.jpg")
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .width(140.dp)
                .height(220.dp)
        )

        Box(
            modifier = Modifier
                .width(140.dp)
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {

        }
    }
}

