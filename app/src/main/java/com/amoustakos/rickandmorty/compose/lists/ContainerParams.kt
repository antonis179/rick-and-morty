package com.amoustakos.rickandmorty.compose.lists

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class ContainerParams(
    val outerPadding: ContainerPadding? = null,
    val innerPadding: ContainerPadding? = null,
    val outerColors: ContainerColors? = null,
    val innerColors: ContainerColors? = null,
    val size: ContainerSize? = null,
    val extraModifiers: Modifier? = null
)

data class ContainerPadding(
    val top: Dp? = null,
    val start: Dp? = null,
    val end: Dp? = null,
    val bottom: Dp? = null
) {
    constructor(
        horizontal: Dp? = null,
        vertical: Dp? = null
    ) : this(
        vertical,
        horizontal,
        horizontal,
        vertical
    )
}

data class ContainerSize(
    val width: Modifier? = null,
    val height: Modifier? = null
)

data class ContainerColors(
    val backgroundColor: Color? = null,
    val backgroundGradient: Modifier? = null
)
