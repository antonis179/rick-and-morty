package com.amoustakos.rickandmorty.compose.lazy

import androidx.compose.runtime.Composable


/**
 * Interface for views in lazy columns/rows
 */
interface ComposeView {

    fun isValidForView(data: ComposeViewData): Boolean

    @Composable
    fun View(data: ComposeViewData)

}
