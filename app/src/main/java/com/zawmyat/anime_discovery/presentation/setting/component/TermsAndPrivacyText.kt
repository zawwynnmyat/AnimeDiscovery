package com.zawmyat.anime_discovery.presentation.setting.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TermsAndPrivacyText(
    onPrivacyPolicyClick: () -> Unit,
    onTermsAndConditionsClick: () -> Unit
) {

    val annotatedText = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
            )
        ) {
            append("If you are using Anime Discovery app, you are agreeing to our ")
        }

        // Add Privacy Policy with a clickable annotation
        pushStringAnnotation(tag = "PRIVACY_POLICY", annotation = "PrivacyPolicy")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append("Privacy Policy")
        }
        pop()

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(" and ")
        }

        // Add Terms & Conditions with a clickable annotation
        pushStringAnnotation(tag = "TERMS_CONDITIONS", annotation = "TermsConditions")

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
            append("Terms & Conditions")
        }

        pop()

        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(".")
        }

    }

    androidx.compose.foundation.text.ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, textAlign = TextAlign.Center),
        modifier = Modifier.padding(16.dp),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "PRIVACY_POLICY", start = offset, end = offset)
                .firstOrNull()?.let {
                    onPrivacyPolicyClick()
                }

            annotatedText.getStringAnnotations(tag = "TERMS_CONDITIONS", start = offset, end = offset)
                .firstOrNull()?.let {
                    onTermsAndConditionsClick()
                }
        },

        )
}