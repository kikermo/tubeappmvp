package org.kikermo.tube.domain.usecase.gettubestatus

import com.facechart.core.api.usecases.UseCase
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import org.kikermo.tube.domain.gateway.TubeGateway
import org.kikermo.tube.persistence.TubeStore
import javax.inject.Inject

class GetTubeStatusUseCase @Inject constructor(
    private val tubeGateway: TubeGateway,
    private val tubeStore: TubeStore
) : UseCase<GetTubeStatusRequest, GetTubeStatusResponse>() {
    override fun apply(upstream: Observable<GetTubeStatusRequest>): ObservableSource<GetTubeStatusResponse> {
        return upstream.switchMap {
            tubeGateway.getTubeLinesStatus()
                .flatMap { tubeStore.updateTubeStatus(it).andThen(Single.just(it)) }
                .map<GetTubeStatusResponse> { GetTubeStatusResponse.LinesFetchedSuccess(it) }
                .toObservable()
                .startWith(tubeStore.getLoadingState())
                .onErrorReturn { GetTubeStatusResponse.Error(it) }
        }
    }

    private fun TubeStore.getLoadingState() =
        getTubeStatus()
            .map { GetTubeStatusResponse.Loading(it) }.toSingle(GetTubeStatusResponse.Loading())
            .toObservable()
}
