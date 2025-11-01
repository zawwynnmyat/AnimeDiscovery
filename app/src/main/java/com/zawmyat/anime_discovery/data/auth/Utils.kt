package com.zawmyat.anime_discovery.data.auth

import android.content.Context
import android.content.Intent
import android.net.Uri

class Utils {

    fun login(context: Context) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("${Constants.BASE_URL}oauth/authorize?client_id=${Constants.CLIENT_ID}&redirect_uri=${Constants.REDIRECT_URL}&response_type=code")
            )
        )
    }
}