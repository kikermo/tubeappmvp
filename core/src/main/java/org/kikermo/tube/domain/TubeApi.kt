package org.kikermo.tube.domain

import com.facechart.core.api.usecases.UseCase
import org.kikermo.tube.domain.usecase.getlinestatus.GetLineStatusRequest
import org.kikermo.tube.domain.usecase.getlinestatus.GetLineStatusResponse
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusRequest
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusResponse

interface TubeApi {
    fun getTubeStatus(): UseCase<GetTubeStatusRequest, GetTubeStatusResponse>

    fun getLineStatus(): UseCase<GetLineStatusRequest, GetLineStatusResponse>
}
