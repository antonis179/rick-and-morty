package com.amoustakos.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController


@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): VM {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(viewModelStoreOwner = parentEntry)
}

@Composable
inline fun <reified VM : ViewModel, reified VMF : ViewModelProvider.Factory> NavBackStackEntry.sharedViewModel(
    navController: NavController,
    noinline creationCallback: (VMF) -> VM
): VM {
    val navGraphRoute = destination.parent?.route
        ?: return hiltViewModel<VM, VMF>(creationCallback = creationCallback)

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel<VM, VMF>(
        viewModelStoreOwner = parentEntry,
        creationCallback = creationCallback
    )
}