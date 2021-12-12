package com.amoustakos.rickandmorty.ui.lazy


/**
 * Interface for view data in lazy columns/rows. Used by [UiView]
 */
interface UiViewData {

    /**
     * Type identifier for lazy column/row.
     * This is required so that a view can be reused
     */
    fun getType(): Any = this::class.simpleName ?: this::class.java

    /**
     * Unique identifier for lazy column/row
     * Warning: If this is not unique the app will crash
     */
    fun getKey(): Any = hashCode()
}


interface UiHeaderViewData : UiViewData

interface HasEnterAnimation {
    var performEnterAnimation: Boolean
}