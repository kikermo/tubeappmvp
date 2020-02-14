package org.kikermo.tube.base

sealed class LoadingState {
    object Loading : LoadingState()
    object Error : LoadingState()
    object Ready : LoadingState()
}

fun List<LoadingState>.getScreenLoadingState(): LoadingState {
    return when {
        any { it is LoadingState.Error } -> LoadingState.Error
        any { it is LoadingState.Loading } -> LoadingState.Loading
        else -> LoadingState.Ready
    }
}
