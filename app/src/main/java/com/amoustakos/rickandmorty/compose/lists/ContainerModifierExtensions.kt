package com.amoustakos.rickandmorty.compose.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amoustakos.rickandmorty.compose.lists.models.ElementPosition

fun ContainerPadding?.toPaddingModifier(): Modifier {
    return Modifier.padding(
        start = this?.start ?: 0.dp,
        top = this?.top ?: 0.dp,
        end = this?.end ?: 0.dp,
        bottom = this?.bottom ?: 0.dp
    )
}

fun ContainerSize?.toSizeModifier(
    defaultWidth: Modifier? = null,
    defaultHeight: Modifier? = null
): Modifier {
    var modifier: Modifier = Modifier

    if (this?.width != null) {
        modifier = modifier.then(width)
    } else if (defaultWidth != null) {
        modifier = modifier.then(defaultWidth)
    }

    if (this?.height != null) {
        modifier = modifier.then(height)
    } else if (defaultHeight != null) {
        modifier = modifier.then(defaultHeight)
    }

    return modifier
}

fun Modifier?.toSafeModifier(): Modifier {
    return this ?: Modifier
}

fun ContainerColors?.toBackgroundModifier(): Modifier {
    this?.backgroundGradient?.let { return it }
    return this?.backgroundColor?.let { Modifier.background(it) } ?: Modifier
}

fun ElementPosition?.toTextAlign(): TextAlign {
    return when (this) {
        ElementPosition.Start -> TextAlign.Start
        ElementPosition.End -> TextAlign.End
        ElementPosition.Center -> TextAlign.Center
        else -> TextAlign.Start
    }
}

fun ElementPosition?.toAlign(): Alignment {
    return when (this) {
        ElementPosition.Start -> Alignment.CenterStart
        ElementPosition.End -> Alignment.CenterEnd
        ElementPosition.Center -> Alignment.Center
        else -> Alignment.Center
    }
}

fun ElementPosition?.toHorizontalAlign(): Alignment.Horizontal {
    return when (this) {
        ElementPosition.Start -> Alignment.Start
        ElementPosition.End -> Alignment.End
        ElementPosition.Center -> Alignment.CenterHorizontally
        else -> Alignment.CenterHorizontally
    }
}

fun ElementPosition?.toVerticalAlign(): Alignment.Vertical {
    return when (this) {
        ElementPosition.Top -> Alignment.Top
        ElementPosition.Bottom -> Alignment.Bottom
        ElementPosition.Center -> Alignment.CenterVertically
        else -> Alignment.CenterVertically
    }
}
