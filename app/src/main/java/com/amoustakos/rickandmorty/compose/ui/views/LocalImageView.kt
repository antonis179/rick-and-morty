package com.amoustakos.rickandmorty.compose.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.lists.models.ImageAttribute.LocalImageAttribute
import com.amoustakos.rickandmorty.compose.lists.toBackgroundModifier
import com.amoustakos.rickandmorty.compose.lists.toPaddingModifier
import com.amoustakos.rickandmorty.compose.lists.toSafeModifier
import com.amoustakos.rickandmorty.compose.lists.toSizeModifier
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import java.util.UUID


@Immutable
data class LocalImageViewData(
    val containerParams: ContainerParams = ContainerParams(),
    val imageAttribute: LocalImageAttribute
) : ComposeViewData {
    private val _key by lazy { UUID.randomUUID().toString() }
    override fun getKey() = _key
}

class LocalImageView : ComposeView {

    override fun isValidForView(data: ComposeViewData) = data is LocalImageViewData

    @Composable
    override fun View(data: ComposeViewData) {
        data as LocalImageViewData
        val imageAttribute = data.imageAttribute

        Box(
            modifier = Modifier
                .then(data.containerParams.size.toSizeModifier())
                .then(data.containerParams.outerColors.toBackgroundModifier())
                .then(data.containerParams.outerPadding.toPaddingModifier())
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .then(data.containerParams.innerColors.toBackgroundModifier())
                    .then(data.containerParams.innerPadding.toPaddingModifier())
                    .then(data.containerParams.extraModifiers.toSafeModifier()),
                painter = imageAttribute.painter,
                colorFilter = imageAttribute.colorFilter,
                contentDescription = imageAttribute.contentDescription,
                contentScale = imageAttribute.contentScale,
                alignment = imageAttribute.alignment
            )
        }
    }
}


@Preview
@Composable
private fun PreviewGenericAsyncImageView() = AppTheme {
    LocalImageView().View(
        LocalImageViewData(
            imageAttribute = LocalImageAttribute(
                painter = painterResource(id = android.R.drawable.ic_dialog_alert),
                contentScale = ContentScale.Fit
            )
        )
    )
}
