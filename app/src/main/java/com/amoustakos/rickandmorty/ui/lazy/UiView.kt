package com.amoustakos.rickandmorty.ui.lazy

import androidx.compose.runtime.Composable


/**
 * Interface for views in lazy columns/rows
 */
interface UiView {

    fun isValidForView(data: UiViewData): Boolean

    @Composable
    fun View(position: Int, data: UiViewData)

}
