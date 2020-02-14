package org.kikermo.tube.tubelinestatuslist

import org.kikermo.tube.base.BaseView
import org.kikermo.tube.domain.model.Line

interface TubeStateListView : BaseView {
    fun showLoading(show: Boolean)

    fun showData(tubeStatus: List<Line>)

    fun showError(show: Boolean)
}