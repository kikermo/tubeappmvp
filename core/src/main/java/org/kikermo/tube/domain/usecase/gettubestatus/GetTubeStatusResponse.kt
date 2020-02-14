package org.kikermo.tube.domain.usecase.gettubestatus

import org.kikermo.tube.domain.model.Line

sealed class GetTubeStatusResponse {
    data class LinesFetchedSuccess(val lines: List<Line>) : GetTubeStatusResponse()

    data class Loading(val lines: List<Line>? = null) : GetTubeStatusResponse()

    data class Error(val throwable: Throwable) : GetTubeStatusResponse()
}
