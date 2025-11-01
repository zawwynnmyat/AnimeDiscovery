package com.zawmyat.anime_discovery.presentation.setting


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.datastore.dataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.presentation.base.MainActivity
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.setting.component.SettingAppBar
import com.zawmyat.anime_discovery.presentation.setting.component.TermsAndPrivacyText
import com.zawmyat.anime_discovery.presentation.shortcut.PinShortcut
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import com.zawmyat.anime_discovery.presentation.studios.component.StudiosAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingPage(
    navController: NavHostController,
    settingViewModel: SettingViewModel = koinViewModel<SettingViewModel>(
        viewModelStoreOwner = LocalContext.current as MainActivity
    ),
    shortcutViewModel: ShortcutViewModel = viewModel(LocalContext.current as MainActivity)
) {

    val currentTheme by settingViewModel.currentTheme.collectAsStateWithLifecycle()

    val context = LocalContext.current.applicationContext

    val ctx = LocalContext.current

    Scaffold(
        topBar = {
            SettingAppBar(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {

                ThemeSettings(
                    currentTheme = currentTheme,
                    onThemeSwitch = {
                        selectedTheme ->
                        settingViewModel.saveThemeSetting(theme = selectedTheme)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                ShortcutSetting(
                    onShortcutAdd = {
                        shortcutId, shortcutLabel, shortcutIcon ->
                        shortcutViewModel.addPinnedShortcut(
                            context = context,
                            shortcutId = shortcutId,
                            shortcutLabel = shortcutLabel,
                            icon = shortcutIcon
                        )

                        Toast.makeText(
                            context,
                            "Successfully added $shortcutLabel to Home screen",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TermsAndPrivacyText(
                    onPrivacyPolicyClick = {
                        openDirectDownloadUrl(
                            siteUrl = "https://doc-hosting.flycricket.io/anime-discovery-privacy-policy/b7b48c89-c48f-4711-886e-9cbff82dcd80/privacy",
                            context = ctx
                        )
                    },
                    onTermsAndConditionsClick = {

                        openDirectDownloadUrl(
                            siteUrl = "https://medium.com/@zawwinmyat.dev/terms-of-service-for-anime-discovery-d954c06efa83",
                            context = ctx
                        )
                    }
                )


            }
        }
    }
}

fun openDirectDownloadUrl(siteUrl: String, context: Context) {
    val browserIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(siteUrl)
    )
    context.startActivity(browserIntent)
}
@Composable
fun ShortcutSetting(
    onShortcutAdd : (shortcutId: String, shortcutLabel: String, shortcutIcon: Int) -> Unit
) {

    val pinShortcuts = listOf(
        PinShortcut(
            shortcutId = "characters",
            shortcutLabel = "Characters",
            icon = R.drawable.baseline_face_24
        ),
        PinShortcut(
            shortcutId = "studios",
            shortcutLabel = "Studios",
            icon = R.drawable.studios_green
        ),
        PinShortcut(
            shortcutId = "staffs",
            shortcutLabel = "Staffs",
            icon = R.drawable.staff_green
        ),
        PinShortcut(
            shortcutId = "reviews",
            "Reviews",
            icon = R.drawable.review_green
        )
    )


    Column {
        Text(
            text = "Add Home Screen Shortcut",
            fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
        )

        Text(
            text = "Note: This will need your pemission to add shortcuts to the Home Screen",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            pinShortcuts.forEach {
                Column(
                    modifier = Modifier.width(55.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(53.dp)
                            .width(53.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = it.icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = it.shortcutLabel,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    IconButton(
                        onClick = {
                            onShortcutAdd(it.shortcutId, it.shortcutLabel, it.icon)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }


    }
}

@Composable
fun ThemeSettings(
    currentTheme: String,
    onThemeSwitch : (currentValue: String) -> Unit
) {
    val radioOptions = listOf("Follow system", "Light Mode", "Dark Mode")
    var (selectedOption, onOptionSelected) = remember {
        mutableStateOf(currentTheme)
    }

    selectedOption = currentTheme

    Column {

        Text(
            text = "Theme Settings",
            fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->

                Card(
                    modifier = Modifier.padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors()
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                    onThemeSwitch(text)
                                },
                                role = Role.RadioButton
                            )
                            .padding(all = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

            }
        }
    }

}