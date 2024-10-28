package com.amoustakos.rickandmorty.compose.animation.list_enter

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


interface AnimatorEnter {
    @Composable
    fun Animate(
        data: HasEnterAnimation,
        modifier: Modifier,
        content: @Composable() (() -> Unit)
    )
}


class NoopEnterAnimator : AnimatorEnter {
    @Composable
    override fun Animate(
        data: HasEnterAnimation,
        modifier: Modifier,
        content: @Composable (() -> Unit)
    ) {
        Box(modifier = modifier) {
            content()
        }
    }
}