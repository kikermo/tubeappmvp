package org.kikermo.tube.domain.gateway

import io.reactivex.Single
import org.kikermo.tube.domain.model.Line

interface TubeGateway {

    fun getTubeLinesStatus(): Single<List<Line>>
}
