@file:OptIn(ExperimentalMaterial3Api::class)

package com.amoustakos.rickandmorty.ui.bars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed interface TopBar {

    @Composable
    fun View(modifier: Modifier)

}

class TitleOnly(private val title: String) : TopBar {
    @Composable
    override fun View(modifier: Modifier) {
        TopAppBar(
            modifier = modifier,
            title = { Text(text = title) }
        )
    }

}