package org.kikermo.tube.persistence

import io.reactivex.Completable
import io.reactivex.Maybe
import org.kikermo.tube.domain.model.Line

interface TubeStore {
    fun getLineStatus(lineId: String): Maybe<Line>

    fun getTubeStatus(): Maybe<List<Line>>

    fun updateTubeStatus(lineList: List<Line>): Completable
}
