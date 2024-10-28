package com.amoustakos.rickandmorty.features.common.views

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amoustakos.rickandmorty.compose.animation.list_enter.AnimatorEnter
import com.amoustakos.rickandmorty.compose.animation.list_enter.HasEnterAnimation
import com.amoustakos.rickandmorty.compose.animation.list_enter.NoopEnterAnimator
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.theme.typography
import java.util.UUID


@Stable
class ImageListingViewData(
    val id: Int,
    val icon: String,
    val title: String,
    val description: String? = null
) : ComposeViewData, HasEnterAnimation {
    private val _key by lazy { UUID.randomUUID().toString() }
    override fun getKey() = _key

    override var performEnterAnimation: Boolean by mutableStateOf(true)
}

class ImageListingView(
    private val enterAnimator: AnimatorEnter?,
    private val onClick: (id: Int) -> Unit
) : ComposeView {

    override fun isValidForView(data: ComposeViewData) = data is ImageListingViewData

    @Composable
    override fun View(data: ComposeViewData) {
        data as ImageListingViewData

        AnimateIfNeeded(enterAnimator = enterAnimator, data = data) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onClick(data.id) },
                shape = RoundedCornerShape(8.dp)
            ) {
                CardContent(data)
            }
        }
    }

    @Composable
    private fun AnimateIfNeeded(
        enterAnimator: AnimatorEnter? = null,
        data : HasEnterAnimation,
        view: @Composable () -> Unit
    ) {
        if (enterAnimator == null) {
            view()
        } else {
            enterAnimator.Animate(data = data, modifier = Modifier) {
                view()
            }
        }
    }

    @Composable
    private fun CardContent(data: ImageListingViewData) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (icon, title, episode) = createRefs()

            AsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.icon)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = android.R.drawable.stat_sys_warning)
            )

            Text(
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(icon.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    },
                text = data.title,
                style = typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .constrainAs(episode) {
                        start.linkTo(icon.end)
                        end.linkTo(parent.end)
                        linkTo(title.bottom, parent.bottom, bias = 0F)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                        verticalChainWeight = 0F
                    },
                text = data.description ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyLarge
            )
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewEpisodeUiViewDark() = AppTheme {
    ImageListingView(NoopEnterAnimator()) {}.View(episodeUiPreviewViewData())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewEpisodeUiViewLight() = AppTheme {
    ImageListingView(NoopEnterAnimator()) {}.View(episodeUiPreviewViewData())
}

private fun episodeUiPreviewViewData() = ImageListingViewData(
    id = 0,
    icon = "http://clipart-library.com/data_images/6103.png",
    title = "Title",
    description = "S01E01"
)