package com.zawmyat.anime_discovery.presentation.photo

import android.content.Context
import androidx.lifecycle.ViewModel
import com.zawmyat.anime_discovery.data.DownloaderImpl
import com.zawmyat.anime_discovery.domain.Downloader

class DownloadViewModel(
    private val downloader: Downloader
) : ViewModel() {

    fun downloadImage(url: String, name: String) {
        downloader.downloadFile(url, name)
    }

}