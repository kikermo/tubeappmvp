package org.kikermo.tube.domain

import org.kikermo.tube.domain.usecase.getlinestatus.GetLineStatusUseCase
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusUseCase
import javax.inject.Inject

class TubeApiImpl @Inject constructor(
    private val getTubeStatusUseCase: GetTubeStatusUseCase,
    private val getLineStatusUseCase: GetLineStatusUseCase
) : TubeApi {
    override fun getLineStatus() = getLineStatusUseCase

    override fun getTubeStatus() = getTubeStatusUseCase
}
