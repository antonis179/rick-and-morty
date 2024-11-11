package com.amoustakos.rickandmorty.compose.lists.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.collections.immutable.PersistentList

open class TextAttribute(
    val text: @Composable () -> String,
    val style: TextStyle,
    val color: ColorAttribute? = null,
    val overflow: TextOverflow? = null,
    val maxLines: Int? = null
)

class ClickableTextAttribute(
    val tag: String,
    text: @Composable () -> String,
    style: TextStyle,
    color: ColorAttribute? = null,
    maxLines: Int? = null,
    overflow: TextOverflow? = null,
    var action: (() -> Unit)? = null
) : TextAttribute(
    text = text,
    style = style,
    color = color,
    maxLines = maxLines,
    overflow = overflow
)

data class AnnotatedTextAttribute(
    val texts: PersistentList<TextAttribute>
)


sealed interface ColorAttribute {
    @Composable
    fun getColor(): Color

    data object OnBackground : ColorAttribute {
        @Composable
        override fun getColor() = MaterialTheme.colorScheme.onBackground
    }
}