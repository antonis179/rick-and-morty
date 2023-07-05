package com.amoustakos.rickandmorty.features.characters.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amoustakos.rickandmorty.data.domain.models.characters.CharacterStatus
import com.amoustakos.rickandmorty.data.domain.models.characters.Location
import com.amoustakos.rickandmorty.ui.lazy.UiView
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import com.amoustakos.rickandmorty.ui.theme.typography


data class CharacterDetailsViewData(
    val name: String,
    val status: CharacterStatus,
    val image: String,
    val gender: String,
    val species: String,
    val created: String,
    val origin: Location,
    val location: Location
) : UiViewData

class CharacterDetailsView : UiView {

    override fun isValidForView(data: UiViewData) = data is CharacterDetailsViewData

    @Composable
    override fun View(position: Int, data: UiViewData) {
        data as CharacterDetailsViewData

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.image)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            val title = """
                ${data.species}, ${data.gender}
            """.trimIndent()

            val description = """
                Origin: ${data.origin.name}
                Current location: ${data.location.name}
            """.trimIndent()

            Text(
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Start),
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = typography.titleLarge,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .padding(top = 32.dp, end = 16.dp, start = 16.dp)
                    .fillMaxWidth()
                    .align(Alignment.Start),
                text = description,
                color = MaterialTheme.colorScheme.onBackground,
                style = typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )

        }

    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewCharacterDetailsViewDark() = AppTheme {
    CharacterDetailsView().View(
        position = 0, data = characterDetailsViewData()
    )
}

@Preview
@Composable
private fun PreviewCharacterDetailsViewLight() = AppTheme {
    CharacterDetailsView().View(
        position = 0, data = characterDetailsViewData()
    )
}

private fun characterDetailsViewData() = CharacterDetailsViewData(
    "Name",
    CharacterStatus.ALIVE,
    "http://clipart-library.com/data_images/6103.png",
    "Male",
    "Human",
    "Date",
    Location("Origin", ""),
    Location("Location", "")
)