package com.amoustakos.rickandmorty.compose.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.lists.toBackgroundModifier
import com.amoustakos.rickandmorty.compose.lists.toPaddingModifier
import com.amoustakos.rickandmorty.compose.lists.toSafeModifier
import com.amoustakos.rickandmorty.compose.lists.toSizeModifier
import java.util.UUID


@Immutable
data class HorizontalLineViewData(
    val containerParams: ContainerParams = ContainerParams()
) : ComposeViewData {
    private val _key by lazy { UUID.randomUUID().toString() }
    override fun getKey() = _key
}


class HorizontalLineView : ComposeView {

    override fun isValidForView(data: ComposeViewData) = data is HorizontalLineViewData

    @Composable
    override fun View(data: ComposeViewData) {
        data as HorizontalLineViewData

        Box(
            modifier = Modifier
                .then(data.containerParams.size.toSizeModifier(Modifier.fillMaxWidth(), null))
                .then(data.containerParams.outerColors.toBackgroundModifier())
                .then(data.containerParams.outerPadding.toPaddingModifier())
                .then(data.containerParams.extraModifiers.toSafeModifier())
        )
    }

}
