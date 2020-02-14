package org.kikermo.tube.network

import io.reactivex.Single
import org.kikermo.tube.domain.gateway.TubeGateway
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.network.model.LineNetworkModel
import org.kikermo.tube.network.model.toDomainEntity
import org.kikermo.tube.network.service.TubeService
import org.kikermo.tube.rx.RxSchedulers
import javax.inject.Inject

class TubeGatewayImpl @Inject constructor(
    private val tubeService: TubeService,
    private val rxSchedulers: RxSchedulers
) : TubeGateway {

    override fun getTubeLinesStatus(): Single<List<Line>> = tubeService
        .getTubeStatus()
        .subscribeOn(rxSchedulers.io())
        .map { lineNetworkModelList ->
            lineNetworkModelList.map(LineNetworkModel::toDomainEntity)
        }
}
