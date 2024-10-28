package com.amoustakos.rickandmorty.features.common.views.loaders

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amoustakos.rickandmorty.compose.theme.AppTheme


@Composable
fun DefaultListLoader(modifier: Modifier = Modifier) {
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DefaultFullPageLoader(modifier: Modifier = Modifier) {
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDefaultListLoaderNight() = AppTheme {
    DefaultListLoader(
        Modifier.background(MaterialTheme.colorScheme.background)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewDefaultListLoaderUi() = AppTheme {
    DefaultListLoader(
        Modifier.background(MaterialTheme.colorScheme.background)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDefaultFullPageLoaderNight() = AppTheme {
    DefaultFullPageLoader(
        Modifier.background(MaterialTheme.colorScheme.background)
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewDefaultFullPageLoaderUi() = AppTheme {
    DefaultFullPageLoader(
        Modifier.background(MaterialTheme.colorScheme.background)
    )
}