package com.zawmyat.anime_discovery.presentation.details.components

import android.text.SpannableStringBuilder
import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.text.util.LinkifyCompat.LinkifyMask
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.toAnnotatedString
import com.zawmyat.anime_discovery.presentation.component.HtmlWebView

@Composable
fun Description(
    description: String
) {

    var isExpandedDescription by remember {
        mutableStateOf(false)
    }

    //Rotation
    var rotationDegree by remember {
        mutableStateOf(0f)
    }

    val animatedRotationDegree by animateFloatAsState(
        targetValue = rotationDegree,
        tween(500)
    )

    //Dp
    var boxHeight by remember {
        mutableStateOf(190.dp)
    }

    val animatedBoxHeight by animateDpAsState(
        targetValue = boxHeight,
    )

    Box() {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedBoxHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = AnnotatedString.fromHtml(
                    htmlString = description
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis
            )



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                if (isExpandedDescription)
                                    Color.Transparent
                                else
                                    MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .height(animatedBoxHeight + 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                IconButton(
                    onClick = {
                        isExpandedDescription = isExpandedDescription.not()
                        if(isExpandedDescription) {
                            rotationDegree = 180f
                            boxHeight = Dp.Unspecified
                        } else {
                            rotationDegree = 0f
                            boxHeight = 195.dp
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.keyboard_arrow_down_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .rotate(
                                degrees = animatedRotationDegree
                            )
                            .size(30.dp)
                    )
                }
            }

        }


    }

}