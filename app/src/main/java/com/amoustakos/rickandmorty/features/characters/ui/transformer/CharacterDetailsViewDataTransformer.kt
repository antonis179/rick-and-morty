package com.amoustakos.rickandmorty.features.characters.ui.transformer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerPadding
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.lists.ContainerSize
import com.amoustakos.rickandmorty.compose.lists.models.ColorAttribute.OnBackground
import com.amoustakos.rickandmorty.compose.lists.models.ImageAttribute.RemoteImageAttribute
import com.amoustakos.rickandmorty.compose.lists.models.TextAttribute
import com.amoustakos.rickandmorty.compose.theme.typography
import com.amoustakos.rickandmorty.compose.ui.views.GenericTextViewData
import com.amoustakos.rickandmorty.compose.ui.views.RemoteImageViewData
import com.amoustakos.rickandmorty.data.domain.models.characters.DomainCharacter
import com.amoustakos.rickandmorty.ui.transformers.ViewDataTransformer
import javax.inject.Inject


class CharacterDetailsViewDataTransformer @Inject constructor(): ViewDataTransformer<DomainCharacter> {

    override fun transform(model: DomainCharacter): List<ComposeViewData> {
        val data = mutableListOf<ComposeViewData>()

        data += RemoteImageViewData(
            containerParams = ContainerParams(
                size = ContainerSize(
                    width = Modifier.fillMaxWidth(),
                    height = Modifier.height(350.dp)
                )
            ),
            imageAttribute = RemoteImageAttribute(
                url = model.image,
                isGif = false,
                crossfade = true,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = model.name
            )
        )

        val title = """
                ${model.species}, ${model.gender}
            """.trimIndent()

        data += GenericTextViewData(
            containerParams = ContainerParams(
                size = ContainerSize(width = Modifier.fillMaxWidth()),
                outerPadding = ContainerPadding(top = 16.dp, start = 16.dp, end = 16.dp)
            ),
            textAttribute = TextAttribute(
                text = title,
                overflow = TextOverflow.Ellipsis,
                style = typography.titleLarge,
                color = OnBackground
            )
        )

        val description = """
                Origin: ${model.origin.name}
                Current location: ${model.location.name}
            """.trimIndent()

        data += GenericTextViewData(
            containerParams = ContainerParams(
                size = ContainerSize(width = Modifier.fillMaxWidth()),
                outerPadding = ContainerPadding(top = 32.dp, start = 16.dp, end = 16.dp)
            ),
            textAttribute = TextAttribute(
                text = description,
                overflow = TextOverflow.Ellipsis,
                style = typography.titleMedium,
                color = OnBackground
            )
        )


        return data
    }


}