package com.amoustakos.rickandmorty.utils

import kotlinx.coroutines.withContext


suspend fun updateInMain(dispatchers: DispatchersWrapper, body: () -> Unit) = withContext(dispatchers.main) {
    body()
}