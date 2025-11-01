package com.zawmyat.anime_discovery.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.zawmyat.anime_discovery.presentation.navigation.Graph
import com.zawmyat.anime_discovery.presentation.setting.SettingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingProcessScreen(
    navController: NavHostController,
    settingViewModel: SettingViewModel = koinViewModel<SettingViewModel>()
) {
    val pages = listOf(
        OnBoardingPage.FirstPage,
        OnBoardingPage.SecondPage,
        OnBoardingPage.ThirdPage
    )

    val pagerState = rememberPagerState()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = pages.size,
            state = pagerState
        ) {
            position ->
            PagerScreen(onBoardingPage = pages[position])
        }

        if(pagerState.currentPage != pages.size - 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(2f),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextButton(
                    onClick = {
                        settingViewModel.saveOnboardingStatus(isShow = true)
                        navController.popBackStack()
                        navController.navigate(Graph.BOTTOM_NAV)
                    }
                ) {
                    Text(
                        text = "SKIP",
                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = MaterialTheme.colorScheme.onSurface,
                    inactiveColor = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                )

                TextButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
                            )
                        }
                    }
                ) {
                    Text(
                        text = "NEXT",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        } else {
            AnimatedVisibility(
                visible = pagerState.currentPage == 2,
            ) {
                Button(
                    onClick = {
                        settingViewModel.saveOnboardingStatus(isShow = true)
                        navController.popBackStack()
                        navController.navigate(Graph.BOTTOM_NAV)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                ) {
                    Text(text = "LET's GO!")
                }
            }
        }

    }

}