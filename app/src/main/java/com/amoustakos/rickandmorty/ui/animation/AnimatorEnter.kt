package com.amoustakos.rickandmorty.ui.animation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


interface AnimatorEnter {
    @Composable
    fun Animate(
        data: HasEnterAnimation,
        modifier: Modifier,
        content: @Composable() (AnimatedVisibilityScope.() -> Unit)
    )
}