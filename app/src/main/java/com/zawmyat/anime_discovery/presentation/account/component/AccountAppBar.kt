package com.zawmyat.anime_discovery.presentation.account.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zawmyat.anime_discovery.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountAppBar(
    onSettingClick : () -> Unit,
    isLogoutButtonShow: Boolean,
    onLogoutClick : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if(isLogoutButtonShow) "Welcome!" else "",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Row {

                IconButton(
                    onClick = { onSettingClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings_24),
                        contentDescription = null
                    )
                }

                
                if(isLogoutButtonShow) {
                    IconButton(
                        onClick = {
                            onLogoutClick()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
                
            }
            

        }
    }

}