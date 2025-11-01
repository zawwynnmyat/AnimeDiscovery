package com.zawmyat.anime_discovery.presentation.component

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import okhttp3.internal.toHexString

fun Context.openActionView(uri: Uri) {
    try {
        Intent(Intent.ACTION_VIEW, uri).apply {
            startActivity(this)
        }
    } catch (e: ActivityNotFoundException) {

    }
}

fun Context.openActionView(url: String) {
    openActionView(Uri.parse(url))
}

fun Int.hexToString() = String.format("#%06X", 0xFFFFFF and this)

@Composable
fun HtmlWebView(
    htmlString: String,
    hardwareEnabled: Boolean = true
) {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    val htmlConverted by remember {
        derivedStateOf {
            generateHtml(
                html = htmlString,
                colorScheme = colorScheme
            )
        }
    }

    val webClient = remember {
        object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.url?.let {
                    context.openActionView(it)
                }
                return true
            }
        }
    }

    AndroidView(
        factory = {
            WebView(it).apply {
               this.webViewClient = webClient
                this.isScrollContainer = false
                this.isVerticalScrollBarEnabled = false
                this.background = ColorDrawable(android.graphics.Color.TRANSPARENT)
                this.loadData(htmlConverted, "", "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

//    WebView(
//        data = htmlConverted,
//        modifier = modifier.fillMaxWidth(),
//        hardwareEnabled = hardwareEnabled,
//        onCreated = { webView ->
//            webView.background = ColorDrawable(android.graphics.Color.TRANSPARENT)
//            webView.isScrollContainer = false
//            webView.isVerticalScrollBarEnabled = false
//        },
//        client = webClient
//    )
}

fun generateHtml(
    html: String,
    colorScheme: ColorScheme
) : String {
    return """
    <HTML>
    <head>
        <meta name='viewport' content='width=device-width, shrink-to-fit=YES, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no'>
    </head>
    ${generateCSS(colorScheme)}
    <BODY>
    <div id="anihyou">${formatCompatibleHtml(html)}</div>
    </BODY>
    </HTML>
""".trimIndent()
}

fun formatCompatibleHtml(html: String): String {
    return html
        // replace AniList markdown [text](link) with html <a>
        .replace(Regex("\\[([^]]+)]\\(([^)]+)\\)"), "<a href=\"\$2\">\$1</a>")
        // replace AniList markdown __bold__ with html <b>
        .replace(Regex("__(.+)__"), "<b>\$1</b>")
        // escaped chars
        .replace("&lt;", "<")
        .replace("&gt;", ">")
}

fun generateCSS(colorScheme: ColorScheme): String {
    return """
    <style type='text/css'>
        ${
        baseCss(
            backgroundColor = colorScheme.background.toArgb().hexToString(),
            fontColor = colorScheme.onBackground.toArgb().hexToString(),
            linkColor = colorScheme.primary.toArgb().hexToString()
        )
    }
        body {
            margin: 16;
            padding: 0;
        }
    </style>
    """.trimIndent()
}

fun baseCss(
    backgroundColor: String,
    fontColor: String,
    linkColor: String
) = """
    body {background-color: $backgroundColor;}
    h1, h2, h3, h4, h5, h6, p, div, dl, ol, ul, pre, blockquote {text-align:left; line-height: 170%; font-family: 'Arial' !important; color: $fontColor; }
    iframe{width:100%; height:250px;}
    a:link {color: $linkColor;}
    A {text-decoration: none;}
    .markdown_spoiler {color: $fontColor; background-color: $fontColor;}
    .markdown_spoiler:not(:hover):not(:focus):not(:active) a:link {color: $fontColor;}
    .markdown_spoiler:hover, .markdown_spoiler:focus, .markdown_spoiler:active {background-color: transparent;}
""".trimIndent()
