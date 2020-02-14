package org.kikermo.tube.tubelinestatusdetails

import org.kikermo.tube.base.LoadingState
import org.kikermo.tube.domain.model.Line

data class TubeStatusDetailsScreenState(
    val loadingState: LoadingState = LoadingState.Loading,
    val line: Line? = null
)
