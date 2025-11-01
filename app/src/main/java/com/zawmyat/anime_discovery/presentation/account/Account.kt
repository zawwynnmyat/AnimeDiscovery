package com.zawmyat.anime_discovery.presentation.account

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.ViewerQuery
import com.zawmyat.anime_discovery.data.auth.Constants
import com.zawmyat.anime_discovery.data.auth.Token
import com.zawmyat.anime_discovery.data.auth.Utils
import com.zawmyat.anime_discovery.presentation.account.component.AccountAppBar
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.setting.SettingViewModel
import com.zawmyat.anime_discovery.presentation.setting.component.TermsAndPrivacyText
import com.zawmyat.anime_discovery.presentation.setting.openDirectDownloadUrl
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun Account(
    navController: NavHostController,
    accessToken : String
) {

    val accountViewModel: AccountViewModel = koinViewModel<AccountViewModel>()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            accountViewModel.fetchAndSaveOptions()
        }
    }

    val isLoadingViewer by accountViewModel.isLoadingViewer.collectAsStateWithLifecycle()
    val viewer by accountViewModel.viewer.collectAsStateWithLifecycle()

    val context = LocalContext.current


    Scaffold(
        topBar = {
            AccountAppBar(
                onSettingClick = {
                    navController.navigate(DetailNavItems.SettingPage.route)
                },
                isLogoutButtonShow = if(accessToken.isNotEmpty()) true else false,
                onLogoutClick = {
                    accountViewModel.logout()
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) {


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            if(accessToken.isEmpty()) {
                LoginScreen()
            } else {

                if(isLoadingViewer) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                         CircularProgressIndicator()

                    }

                } else {

                    viewer?.let {
                        AccountDetails(
                            context = context,
                            viewer = it
                        )
                    } ?: ErrorLoadingAccountDetails()

                }

            }

        }

    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.anime),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.3f)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Login/Register",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {

            Text(
                text = "Important to Know",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "You'll be redirected to anilist.co to login/register. Make sure the URL is anilist.co before entering your email and password.",
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        Utils().login(context = context)
                    }
                ) {
                    Text(text = "Login with AniList")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TermsAndPrivacyText(
                onPrivacyPolicyClick = {
                    openDirectDownloadUrl(
                        siteUrl = "https://doc-hosting.flycricket.io/anime-discovery-privacy-policy/b7b48c89-c48f-4711-886e-9cbff82dcd80/privacy",
                        context = context
                    )
                },
                onTermsAndConditionsClick = {

                    openDirectDownloadUrl(
                        siteUrl = "https://medium.com/@zawwinmyat.dev/terms-of-service-for-anime-discovery-d954c06efa83",
                        context = context
                    )
                }
            )
        }

    }


}

@Composable
fun ErrorLoadingAccountDetails() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
    ) {
        Text(text = "Error Loading Account Details")
    }
}

@Composable
fun AccountDetails(
    viewer: ViewerQuery.Viewer,
    context: Context
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data(viewer.avatar?.medium ?: "")
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(90.dp)
                    .height(90.dp)
            )
            
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(text = viewer.name)
            
            Text(
                text = viewer.about ?: ""
            )
        }
        
        Spacer(modifier = Modifier.height(10.dp))


    }
}