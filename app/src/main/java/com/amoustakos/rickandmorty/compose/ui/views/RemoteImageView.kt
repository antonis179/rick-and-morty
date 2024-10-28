package com.amoustakos.rickandmorty.compose.ui.views

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerPadding
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.lists.models.ImageAttribute.RemoteImageAttribute
import com.amoustakos.rickandmorty.compose.lists.toBackgroundModifier
import com.amoustakos.rickandmorty.compose.lists.toPaddingModifier
import com.amoustakos.rickandmorty.compose.lists.toSafeModifier
import com.amoustakos.rickandmorty.compose.lists.toSizeModifier
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import java.util.UUID


@Immutable
data class RemoteImageViewData(
    val containerParams: ContainerParams = ContainerParams(),
    val imageAttribute: RemoteImageAttribute
) : ComposeViewData {
    private val _key by lazy { UUID.randomUUID().toString() }
    override fun getKey() = _key
}

class RemoteImageView : ComposeView {

    override fun isValidForView(data: ComposeViewData) = data is RemoteImageViewData

    @Composable
    override fun View(data: ComposeViewData) {
        data as RemoteImageViewData
        val imageAttribute = data.imageAttribute

        var imageLoaderBuilder = ImageLoader.Builder(LocalContext.current)

        if (imageAttribute.isGif) {
            imageLoaderBuilder = imageLoaderBuilder.components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
        }

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(imageAttribute.url)
            .crossfade(imageAttribute.crossfade)
            .build()
        val imageLoader = imageLoaderBuilder.build()

        Box(
            modifier = Modifier
                .then(data.containerParams.size.toSizeModifier())
                .then(data.containerParams.outerColors.toBackgroundModifier())
                .then(data.containerParams.outerPadding.toPaddingModifier())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .then(data.containerParams.innerColors.toBackgroundModifier())
                    .then(data.containerParams.innerPadding.toPaddingModifier())
                    .then(data.containerParams.extraModifiers.toSafeModifier()),
                imageLoader = imageLoader,
                model = imageRequest,
                contentDescription = imageAttribute.contentDescription,
                contentScale = imageAttribute.contentScale,
                alignment = imageAttribute.alignment,
                placeholder = imageAttribute.placeholder,
                error = imageAttribute.error
            )
        }
    }
}


@Preview
@Composable
private fun PreviewGenericAsyncImageView() = AppTheme {
    RemoteImageView().View(
        RemoteImageViewData(
            ContainerParams(outerPadding = ContainerPadding(top = 39.dp)),
            RemoteImageAttribute(
                url = "https://www.vodafone.gr/images/1490723196396-help-faq-hi-icon-hi_icon.png",
                contentScale = ContentScale.FillWidth
            )
        )
    )
}
