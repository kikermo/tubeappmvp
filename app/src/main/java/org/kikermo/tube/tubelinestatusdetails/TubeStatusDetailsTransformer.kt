package org.kikermo.tube.tubelinestatusdetails

import io.reactivex.ObservableTransformer
import org.kikermo.tube.base.FlowEvent
import org.kikermo.tube.base.LifecycleEvent
import org.kikermo.tube.base.ScreenAction
import org.kikermo.tube.base.Transformer
import org.kikermo.tube.domain.TubeApi
import org.kikermo.tube.domain.usecase.getlinestatus.GetLineStatusRequest
import org.kikermo.tube.navigation.TubeStatusDetailsNavigationAction
import javax.inject.Inject

class TubeStatusDetailsTransformer @Inject constructor(
    private val tubeApi: TubeApi,
    private val navigationAction: TubeStatusDetailsNavigationAction
) : Transformer<TubeStatusDetailsScreenState> {
    override fun getTransformers() = listOf(
        fetchTubeStatus
    )

    private val fetchTubeStatus =
        ObservableTransformer<FlowEvent<ScreenAction, TubeStatusDetailsScreenState>, Any> { flowEvent ->
            flowEvent.filter {
                it.action is LifecycleEvent.Created
            }
                .map { GetLineStatusRequest(navigationAction.lineId) }
                .compose(tubeApi.getLineStatus())
        }
}
