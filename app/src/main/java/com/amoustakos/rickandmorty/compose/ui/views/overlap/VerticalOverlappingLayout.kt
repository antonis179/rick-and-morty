package com.amoustakos.rickandmorty.compose.ui.views.overlap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp


/**
 * Vertically overlaps 2 layouts using the offset specified.
 * @param content: a Layout with EXACTLY 2 views inside.
 */
@Composable
@Throws(IllegalArgumentException::class)
fun VerticalOverlappingLayout(
    modifier: Modifier = Modifier,
    overlap: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        require(measurables.size == 2) {
            throw IllegalArgumentException("Exactly 2 composables must be used.")
        }

        val topView = measurables[0]
        val bottomView = measurables[1]
        val overlapSize = overlap.toPx().toInt()

        val topConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0
        )
        val topPlaceable = topView.measure(topConstraints)

        /*
         * Calculate the bottom view max height by subtracting the top view height and adding
         * the overlap size (that will subtracted during layout causing extra space to be added)
         */
        val bottomConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
            maxHeight = constraints.maxHeight - topPlaceable.height + overlapSize
        )
        val bottomPlaceable = bottomView.measure(bottomConstraints)


        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            topPlaceable.placeRelative(
                x = 0,
                y = 0
            )
            bottomPlaceable.placeRelative(
                x = 0,
                y = topPlaceable.height - overlapSize
            )
        }
    }
}
