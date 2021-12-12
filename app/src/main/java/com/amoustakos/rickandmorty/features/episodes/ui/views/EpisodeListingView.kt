package com.amoustakos.rickandmorty.features.episodes.ui.views

import android.R
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amoustakos.rickandmorty.ui.lazy.AnimatorEnter
import com.amoustakos.rickandmorty.ui.lazy.HasEnterAnimation
import com.amoustakos.rickandmorty.ui.lazy.SlideInBottomAnimator
import com.amoustakos.rickandmorty.ui.lazy.UiView
import com.amoustakos.rickandmorty.ui.lazy.UiViewData
import com.amoustakos.rickandmorty.ui.theme.AppTheme
import com.amoustakos.rickandmorty.ui.theme.typography


@Stable
class EpisodeListingViewData(
    val id: Int,
    val icon: String,
    val title: String,
    val description: String,
    val characters: List<String>
) : UiViewData, HasEnterAnimation {
    override var performEnterAnimation: Boolean by mutableStateOf(true)
}

class EpisodeListingView(
    private val enterAnimator: AnimatorEnter,
    private val onClick: (ids: List<String>) -> Unit
) : UiView {

    override fun isValidForView(data: UiViewData) = data is EpisodeListingViewData

    @Composable
    override fun View(position: Int, data: UiViewData) {
        data as EpisodeListingViewData

        enterAnimator.Animate(data = data, modifier = Modifier) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onClick(data.characters) },
                shape = RoundedCornerShape(8.dp)
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ConstraintLayout(
                        constraintSet = constraints(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .layoutId(ICON_ID)
                                .size(120.dp)
                                .align(Alignment.Center),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(data.icon)
                                .crossfade(true)
                                .build(),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            placeholder = painterResource(id = R.drawable.stat_sys_warning) //TODO
                        )

                        Text(
                            modifier = Modifier
                                .layoutId(TITLE_ID)
                                .padding(top = 16.dp, end = 16.dp, start = 16.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterStart),
                            text = data.title,
                            style = typography.titleMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            modifier = Modifier
                                .layoutId(EPISODE_ID)
                                .padding(end = 16.dp, start = 16.dp, top = 8.dp, bottom = 8.dp)
                                .fillMaxWidth()
                                .align(Alignment.CenterStart),
                            text = data.description,
                            style = typography.bodyLarge
                        )
                    }
                }
            }
        }
    }


    @Composable
    private fun constraints(): ConstraintSet {
        val constraints = ConstraintSet {
            val icon = createRefFor(ICON_ID)
            val title = createRefFor(TITLE_ID)
            val episode = createRefFor(EPISODE_ID)

            constrain(icon) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }

            constrain(title) {
                top.linkTo(parent.top)
                start.linkTo(icon.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }

            constrain(episode) {
                start.linkTo(icon.end)
                end.linkTo(parent.end)
                linkTo(title.bottom, parent.bottom, bias = 0F)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
                verticalChainWeight = 0F
            }
        }
        return constraints
    }

    companion object {
        const val TITLE_ID = "TITLE_ID"
        const val ICON_ID = "ICON_ID"
        const val EPISODE_ID = "EPISODE_ID"
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun previewEpisodeUiViewDark() = AppTheme {
    EpisodeListingView(SlideInBottomAnimator()) {}.View(
        position = 0, data = episodeUiPreviewViewData()
    )
}

@Preview
@Composable
private fun previewEpisodeUiViewLight() = AppTheme {
    EpisodeListingView(SlideInBottomAnimator()) {}.View(
        position = 0, data = episodeUiPreviewViewData()
    )
}

private fun episodeUiPreviewViewData() = EpisodeListingViewData(
    0,
    "http://clipart-library.com/data_images/6103.png",
    "Title",
    "S01E01",
    emptyList()
)