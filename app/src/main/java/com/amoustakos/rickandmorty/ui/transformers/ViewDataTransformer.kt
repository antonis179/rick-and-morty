package com.amoustakos.rickandmorty.ui.transformers

import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData


interface ViewDataTransformer<Model> {

    fun transform(model: Model): List<ComposeViewData>

}