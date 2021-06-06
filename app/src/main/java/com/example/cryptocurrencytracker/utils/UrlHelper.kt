package com.example.cryptocurrencytracker.utils

import com.example.cryptocurrencytracker.Config.BASE_IMAGE_URL

object UrlHelper {
    fun buildImageUrl(imageUrl: String): String {
        return BASE_IMAGE_URL+imageUrl
    }
}