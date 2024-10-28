package com.amoustakos.rickandmorty.compose.ui.views.video

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.ControllerVisibilityListener
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import com.amoustakos.rickandmorty.compose.lists.ContainerPadding
import com.amoustakos.rickandmorty.compose.lists.ContainerParams
import com.amoustakos.rickandmorty.compose.lists.ContainerSize
import com.amoustakos.rickandmorty.compose.lists.toBackgroundModifier
import com.amoustakos.rickandmorty.compose.lists.toPaddingModifier
import com.amoustakos.rickandmorty.compose.lists.toSizeModifier
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import java.util.UUID

@Immutable
data class VideoPlayerViewData(
    val containerParams: ContainerParams = ContainerParams(),
    val videoUri: String,
    @DrawableRes val audioIconOn: Int,
    @DrawableRes val audioIconOff: Int
) : ComposeViewData {
    private val _key by lazy { UUID.randomUUID().toString() }
    override fun getKey() = _key
}

class VideoPlayerView : ComposeView {
    override fun isValidForView(data: ComposeViewData) = data is VideoPlayerViewData

    @Composable
    override fun View(data: ComposeViewData) {
        data as VideoPlayerViewData

        VideoView(
            modifier = Modifier
                .then(
                    data.containerParams.size.toSizeModifier(
                        Modifier.fillMaxWidth(),
                        Modifier.height(200.dp)
                    )
                )
                .then(data.containerParams.outerColors.toBackgroundModifier())
                .then(data.containerParams.outerPadding.toPaddingModifier()),
            data = data
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoView(
    modifier: Modifier = Modifier,
    data: VideoPlayerViewData
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isMuted by remember { mutableStateOf(false) }
    var audioButtonShown by remember { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer
            .Builder(context)
            .setDeviceVolumeControlEnabled(true)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .build().apply {
                volume = if (isMuted) 0f else 1f
            }
    }

    val mediaSource = remember(data.videoUri) {
        MediaItem.fromUri(data.videoUri)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) exoPlayer.pause()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    setControllerVisibilityListener(ControllerVisibilityListener { visibility ->
                        audioButtonShown = visibility == View.VISIBLE
                    })
                }
            }
        )

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd),
            visible = audioButtonShown,
            enter = fadeIn(animationSpec = tween(750)),
            exit = fadeOut(animationSpec = tween(750))
        ) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable {
                        isMuted = !isMuted
                        exoPlayer.volume = if (isMuted) 0f else 1f
                    },
                painter = if (isMuted) {
                    painterResource(data.audioIconOff)
                } else {
                    painterResource(data.audioIconOn)
                },
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
private fun PreviewVideoPlayerView() = AppTheme {
    VideoPlayerView().View(
        VideoPlayerViewData(
            containerParams = ContainerParams(
                outerPadding = ContainerPadding(
                    top = 24.dp
                ),
                size = ContainerSize(
                    width = Modifier.fillMaxWidth(),
                    height = Modifier.wrapContentHeight()
                )
            ),
            videoUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            audioIconOff = -1,
            audioIconOn = -1
        )
    )
}
