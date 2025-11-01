package com.zawmyat.anime_discovery.data.utils

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import java.text.DecimalFormat
import androidx.compose.ui.text.font.FontStyle
import java.text.SimpleDateFormat
import java.util.Locale

fun format(amount: Int):String{
    val numberFormat = DecimalFormat("#,###")
    return numberFormat.format(amount)
}

fun formatFloat(amount: Float):String{
    val numberFormat = DecimalFormat("#,###")
    return numberFormat.format(amount)
}

fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
            }
            is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }
    }
}

val simpleDateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.ENGLISH)

fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)

fun getDateString(time: Int) : String = simpleDateFormat.format(time * 1000L)

fun navigateToWebSite(context: Context, siteUrl: String) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(siteUrl)
    )

    context.startActivity(intent)
}