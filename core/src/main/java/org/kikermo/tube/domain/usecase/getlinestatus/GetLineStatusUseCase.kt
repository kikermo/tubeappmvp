package org.kikermo.tube.domain.usecase.getlinestatus

import com.facechart.core.api.usecases.UseCase
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import org.kikermo.tube.domain.gateway.TubeGateway
import org.kikermo.tube.persistence.TubeStore
import javax.inject.Inject

class GetLineStatusUseCase @Inject constructor(
    private val tubeGateway: TubeGateway,
    private val tubeStore: TubeStore
) : UseCase<GetLineStatusRequest, GetLineStatusResponse>() {
    override fun apply(upstream: Observable<GetLineStatusRequest>): ObservableSource<GetLineStatusResponse> {
        return upstream.switchMap { request ->
            tubeGateway.getTubeLinesStatus()
                .flatMap { tubeStore.updateTubeStatus(it).andThen(Single.just(it)) }
                .map { lines ->
                    val line = lines.find { it.id == request.id }
                    line?.let { GetLineStatusResponse.LineFetchedSuccess(it) }
                        ?: GetLineStatusResponse.Error(Exception("Line not found"))
                }
                .toObservable()
                .startWith(tubeStore.getLoadingState(request.id))
                .onErrorReturn { GetLineStatusResponse.Error(it) }
        }
    }

    private fun TubeStore.getLoadingState(id: String) =
        getLineStatus(id)
            .map { GetLineStatusResponse.Loading(it) }.toSingle(GetLineStatusResponse.Loading())
            .toObservable()
}
