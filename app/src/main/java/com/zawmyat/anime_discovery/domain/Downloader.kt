package com.zawmyat.anime_discovery.domain

interface Downloader {
    fun downloadFile(url: String, name: String) : Long
}