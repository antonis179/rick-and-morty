package com.amoustakos.rickandmorty.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import com.amoustakos.rickandmorty.compose.lists.models.TextAttribute


@Composable
fun TextAttribute.styleWithColorAttribute(): TextStyle {
    return color?.let { style.copy(color = it.getColor()) } ?: style
}

@Composable
fun TextAttribute.makeSpanStyle(): SpanStyle {
    return styleWithColorAttribute().toSpanStyle()
}
