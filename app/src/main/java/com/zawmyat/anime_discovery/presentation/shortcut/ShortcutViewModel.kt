package com.zawmyat.anime_discovery.presentation.shortcut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModel
import com.zawmyat.anime_discovery.presentation.base.MainActivity

class ShortcutViewModel() : ViewModel() {

    var shortcutName by mutableStateOf<ShortcutName?>(null)
        private set

    fun onShortcutClick(name: ShortcutName?) {
        shortcutName = name
    }

    fun handleIntent(intent: Intent?) {
        intent?.let {
            when(it.getStringExtra("shortcut_id")) {
                "characters" -> onShortcutClick(ShortcutName.CHARACTER)
                "studios" -> onShortcutClick(ShortcutName.STUDIOS)
                "staffs" -> onShortcutClick(ShortcutName.STAFFS)
                "reviews" -> onShortcutClick(ShortcutName.REVIEWS)
            }
        }
    }

    fun addPinnedShortcut(
        context: Context,
        shortcutId: String,
        shortcutLabel: String,
        icon: Int
    ) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val shortcutManager = context.getSystemService<ShortcutManager>()

        shortcutManager?.let {
            if(it.isRequestPinShortcutSupported) {
                val shortcut = ShortcutInfo.Builder(context, shortcutId)
                    .setShortLabel(shortcutLabel)
                    .setLongLabel(shortcutLabel)
                    .setIcon(
                        Icon.createWithResource(
                            context,
                            icon
                        )
                    )
                    .setIntent(
                        Intent(
                            context,
                            MainActivity::class.java
                        ).apply {
                            action = Intent.ACTION_VIEW
                            putExtra("shortcut_id", shortcutId)
                        }
                    )
                    .build()

                val callbackIntent = it.createShortcutResultIntent(shortcut)
                val successPendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    callbackIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                it.requestPinShortcut(shortcut, successPendingIntent.intentSender)
            } else {
                Toast.makeText(
                    context,
                    "App shortcut doesn't support on your device.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } ?:
        Toast.makeText(
            context,
            "App shortcut doesn't support on your device.",
            Toast.LENGTH_LONG
        ).show()


    }

}