package com.amoustakos.rickandmorty.compose.animation.list_enter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import javax.inject.Inject


class SlideInBottomAnimator @Inject constructor() : AnimatorEnter {
    @Composable
    override fun Animate(
        data: HasEnterAnimation,
        modifier: Modifier,
        content: @Composable() (() -> Unit)
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