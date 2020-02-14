package org.kikermo.tube.domain.usecase.getlinestatus

import org.kikermo.tube.domain.model.Line

sealed class GetLineStatusResponse {
    data class LineFetchedSuccess(val line: Line) : GetLineStatusResponse()

    data class Loading(val line: Line? = null) : GetLineStatusResponse()

    data class Error(val throwable: Throwable) : GetLineStatusResponse()
}
