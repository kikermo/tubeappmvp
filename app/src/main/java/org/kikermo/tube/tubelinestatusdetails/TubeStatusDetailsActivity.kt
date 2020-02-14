package org.kikermo.tube.tubelinestatusdetails

import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_tube_status_details.*
import org.kikermo.tube.R
import org.kikermo.tube.base.BaseMVIActivity
import org.kikermo.tube.base.LoadingState
import org.kikermo.tube.base.ScreenAction
import org.kikermo.tube.utils.show
import org.kikermo.tube.utils.toTranslucidColour

class TubeStatusDetailsActivity :
    BaseMVIActivity<TubeStatusDetailsScreenState, TubeStatusDetailsViewModel>() {

    override val activityLayout = R.layout.activity_tube_status_details

    private val actionPublisher = PublishSubject.create<ScreenAction>()

    override val actions: Observable<ScreenAction>
        get() = actionPublisher

    override fun render(viewState: TubeStatusDetailsScreenState) {
        tube_status_details_loading_view.show(viewState.loadingState == LoadingState.Loading)
        if (viewState.loadingState == LoadingState.Error) renderNetworkError()

        viewState.line?.run {
            tube_status_details_title.text = name
            tube_status_details_status.text = status?.description ?: ""
            tube_status_details_severity.text = status?.severity.toString()
            tube_status_details_root.setBackgroundColor(toTranslucidColour())
            tube_status_details_reason.text = status?.reason ?: ""
        }
    }

    private fun renderNetworkError() {
        Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show()
    }
}
