package com.amoustakos.rickandmorty.compose.ui.views.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.amoustakos.rickandmorty.compose.lazy.ComposeView
import com.amoustakos.rickandmorty.compose.lazy.ComposeViewData
import kotlinx.collections.immutable.ImmutableList


@Composable
fun PaginatedLazyColumn(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    paginateOffset: Int = 1,
    listState: LazyListState = rememberLazyListState(),
    views: ImmutableList<ComposeView>,
    items: List<ComposeViewData>,
    endSpacer: (@Composable () -> Unit)? = null,
    loadingItem: @Composable () -> Unit,
    loadMore: () -> Unit
) {

    val reachedBottom: Boolean by remember { derivedStateOf { listState.reachedBottom(paginateOffset) } }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && !loading) loadMore()
    }

    LazyColumn(modifier = modifier, state = listState) {

        items(
            items = items,
            key = { item -> item.getKey() },
            contentType = { item -> item.getType() }
        ) { item ->
            LazyItem(views, item)
        }

        item {
            if (loading) loadingItem() else endSpacer?.invoke()
        }

    }
}

private fun LazyListState.reachedBottom(paginateOffset: Int): Boolean {
    return (layoutInfo.visibleItemsInfo.lastOrNull()?.index
        ?: 0) >= (layoutInfo.totalItemsCount - paginateOffset)
}