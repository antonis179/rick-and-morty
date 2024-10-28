package com.amoustakos.rickandmorty.compose.lists.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.amoustakos.rickandmorty.compose.lists.toTextAlign
import com.amoustakos.rickandmorty.compose.theme.font
import kotlinx.collections.immutable.PersistentList

open class TextAttribute(
    val text: String,
    val style: TextStyle,
    val color: ColorAttribute? = null,
    val overflow: TextOverflow? = null,
    val maxLines: Int? = null
) {

    constructor(
        text: String,
        color: Color? = null,
        backgroundColor: Color? = null,
        alignment: ElementPosition? = null,
        fontSize: TextUnit? = null,
        fontFamily: FontFamily = font,
        fontWeight: FontWeight? = null,
        lineHeight: TextUnit? = null,
        letterSpacing: TextUnit? = null,
        fontStyle: FontStyle? = null,
        textDecoration: TextDecoration? = null,
        overflow: TextOverflow? = null,
        maxLines: Int? = null
    ) : this(
        text = text,
        style = TextStyle(
            fontSize = fontSize ?: TextUnit.Unspecified,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            color = color ?: Color.Unspecified,
            background = backgroundColor ?: Color.Unspecified,
            textDecoration = textDecoration,
            textAlign = alignment.toTextAlign(),
            lineHeight = lineHeight ?: TextUnit.Unspecified,
            letterSpacing = letterSpacing ?: TextUnit.Unspecified
        ),
        overflow = overflow,
        maxLines = maxLines
    )
}

class ClickableTextAttribute(
    val tag: String,
    text: String,
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