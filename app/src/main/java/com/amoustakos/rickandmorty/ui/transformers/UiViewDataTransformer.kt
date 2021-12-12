package com.amoustakos.rickandmorty.ui.transformers

import com.amoustakos.rickandmorty.ui.lazy.UiViewData


interface UiViewDataTransformer<M, D: UiViewData> {

    fun transform(model: M): List<D>

}