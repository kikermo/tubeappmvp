package org.kikermo.tube.tubelinestatusdetails

import org.kikermo.tube.base.LoadingState
import org.kikermo.tube.base.Reducer
import org.kikermo.tube.domain.usecase.getlinestatus.GetLineStatusResponse
import javax.inject.Inject

class TubeStatusDetailsScreenReducer @Inject constructor() : Reducer<TubeStatusDetailsScreenState> {
    override fun reduce(
        currentState: TubeStatusDetailsScreenState,
        event: Any
    ): TubeStatusDetailsScreenState {
        return when (event) {
            is GetLineStatusResponse -> reduceLineStatus(currentState, event)
            else -> currentState
        }
    }

    private fun reduceLineStatus(
        currentState: TubeStatusDetailsScreenState,
        event: GetLineStatusResponse
    ) = when (event) {
        is GetLineStatusResponse.Loading -> currentState.copy(
            loadingState = LoadingState.Loading,
            line = event.line
        )
        is GetLineStatusResponse.Error -> currentState.copy(
            loadingState = LoadingState.Error
        )
        is GetLineStatusResponse.LineFetchedSuccess -> currentState.copy(
            loadingState = LoadingState.Ready,
            line = event.line
        )
    }
}
