package com.amoustakos.rickandmorty.ui.lazy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import javax.inject.Inject


/**
 * Interface for views in lazy columns/rows
 */
interface UiView {

    fun isValidForView(data: UiViewData): Boolean

    @Composable
    fun View(position: Int, data: UiViewData)

}

interface AnimatorEnter {
    @Composable
    fun Animate(
        data: HasEnterAnimation,
        modifier: Modifier,
        content: @Composable() (AnimatedVisibilityScope.() -> Unit)
    )
}

class SlideInBottomAnimator @Inject constructor() : AnimatorEnter {
    @Composable
    override fun Animate(
        data: HasEnterAnimation,
        modifier: Modifier,
        content: @Composable() (AnimatedVisibilityScope.() -> Unit)
    ) {
        AnimatedVisibility(
            visible = !data.performEnterAnimation,
            modifier = modifier,
            enter = fadeIn(initialAlpha = 0.0f) + slideInVertically(
                tween(
                    durationMillis = 500,
                    delayMillis = 0,
                    easing = FastOutSlowInEasing
                ),
                initialOffsetY = { it * 6 }
            )
        ) {
            content()
        }

        if (data.performEnterAnimation) {
            data.performEnterAnimation = false
        }
    }
}