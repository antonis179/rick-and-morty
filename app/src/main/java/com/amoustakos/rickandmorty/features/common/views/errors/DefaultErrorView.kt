package com.amoustakos.rickandmorty.features.common.views.errors

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.amoustakos.rickandmorty.R
import com.amoustakos.rickandmorty.compose.theme.AppTheme
import com.amoustakos.rickandmorty.compose.theme.typography


@Composable
fun DefaultErrorView(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (image, description) = createRefs()

        Image(
            modifier = Modifier
                .padding(top = 128.dp)
                .padding(horizontal = 16.dp)
                .size(160.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentDescription = null,
            painter = painterResource(id = android.R.drawable.ic_dialog_alert),
            colorFilter = tint(MaterialTheme.colorScheme.error)
        )

        Text(
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp, start = 16.dp)
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .constrainAs(description) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    linkTo(top = image.bottom, bottom = parent.bottom, bias = 0f)
                },
            text = stringResource(id = R.string.error_message_default),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = typography.titleLarge,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDefaultErrorViewNight() = AppTheme {
    DefaultErrorView(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewDefaultErrorViewUi() = AppTheme {
    DefaultErrorView(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
}

