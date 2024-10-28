package com.amoustakos.rickandmorty.compose.lists.models

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale


sealed class ImageAttribute(
    val colorFilter: ColorFilter? = null,
    val contentDescription: String? = null,
    val alignment: Alignment = Alignment.Center,
    val contentScale: ContentScale = ContentScale.Fit,
) {

    class LocalImageAttribute(
        val painter: Painter,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        colorFilter: ColorFilter? = null,
        contentDescription: String? = null
    ) : ImageAttribute(
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale
    )

    class RemoteImageAttribute(
        val url: String,
        val placeholder: Painter? = null,
        val error: Painter? = null,
        val isGif: Boolean = false,
        val crossfade: Boolean = true,
        alignment: Alignment = Alignment.Center,
        contentScale: ContentScale = ContentScale.Fit,
        colorFilter: ColorFilter? = null,
        contentDescription: String? = null
    ) : ImageAttribute(
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale
    )

}