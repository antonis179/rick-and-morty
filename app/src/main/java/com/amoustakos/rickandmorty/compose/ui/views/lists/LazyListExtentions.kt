package com.amoustakos.rickandmorty.compose.ui.views.lists

import androidx.compose.runtime.Composable
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import timber.log.Timber


@Composable
fun LazyItem(
    views: List<ComposeView>,
    item: ComposeViewData
) {
    views.find { it.isValidForView(item) }?.View(item)
        ?: Timber.e(
            IllegalArgumentException("Tried to parse invalid ComposeViewData: $item")
        )
}