package com.amoustakos.rickandmorty.compose.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.amoustakos.rickandmorty.compose.extensions.styleWithColorAttribute
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.lists.models.ColorAttribute
import com.amoustakos.rickandmorty.compose.lists.models.TextAttribute
import com.amoustakos.rickandmorty.compose.lists.toBackgroundModifier
import com.amoustakos.rickandmorty.compose.lists.toPaddingModifier
import com.amoustakos.rickandmorty.compose.lists.toSafeModifier
import com.amoustakos.rickandmorty.compose.lists.toSizeModifier
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.theme.typography
import java.util.UUID


@Immutable
data class GenericTextViewData(
    val containerParams: ContainerParams = ContainerParams(),
    val textAttribute: TextAttribute
) : ComposeViewData {
    private val _key by lazy { UUID.randomUUID().toString() }
    override fun getKey() = _key
}


class GenericTextView : ComposeView {

    override fun isValidForView(data: ComposeViewData) = data is GenericTextViewData

    @Composable
    override fun View(data: ComposeViewData) {
        data as GenericTextViewData

        Box(
            modifier = Modifier
                .then(data.containerParams.size.toSizeModifier(Modifier.fillMaxWidth(), null))
                .then(data.containerParams.outerColors.toBackgroundModifier())
                .then(data.containerParams.outerPadding.toPaddingModifier())
                .then(data.containerParams.extraModifiers.toSafeModifier())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(data.containerParams.innerColors.toBackgroundModifier())
                    .then(data.containerParams.innerPadding.toPaddingModifier()),
                text = data.textAttribute.text(),
                style = data.textAttribute.styleWithColorAttribute(),
                overflow = data.textAttribute.overflow ?: TextOverflow.Visible,
                maxLines = data.textAttribute.maxLines ?: Int.MAX_VALUE
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewGenericTextView() = AppTheme {
    GenericTextView().View(
        data = GenericTextViewData(
            textAttribute = TextAttribute(
                text = {"Text"},
                style = typography.titleMedium,
                color = ColorAttribute.OnBackground
            )
        )
    )
}
