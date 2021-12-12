package com.amoustakos.rickandmorty.ui.loaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DefaultListLoadingIndicator(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
) {
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
fun DefaultFullPageLoadingIndicator(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }
}