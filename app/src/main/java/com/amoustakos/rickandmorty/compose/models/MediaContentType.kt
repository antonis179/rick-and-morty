package com.amoustakos.rickandmorty.compose.models

import timber.log.Timber
import kotlin.text.contains


sealed interface MediaContentType {

    fun fromStringType(type: String): MediaContentType? = when {
        type == "image/gif" -> GifContentType
        type.contains("image/") -> ImageContentType
        type.contains("video/") -> VideoContentType
        else -> {
            Timber.i("Unknown media content type: $type")
            null
        }
    }

    data object GifContentType : MediaContentType
    data object ImageContentType : MediaContentType
    data object VideoContentType : MediaContentType
}
