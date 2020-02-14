package org.kikermo.tube.persistence

import io.reactivex.Observable
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.rx.RxSchedulers
import org.kikermo.tube.persistence.model.fromDomainModel
import org.kikermo.tube.persistence.model.toDomainModel
import javax.inject.Inject

class TubeStoreImpl @Inject constructor(
    private val lineDao: LineDao,
    private val rxSchedulers: RxSchedulers
) : TubeStore {
    override fun getLineStatus(lineId: String) = lineDao
        .getLine(lineId)
        .subscribeOn(rxSchedulers.io())
        .map { it.toDomainModel() }

    override fun getTubeStatus() = lineDao
        .getAllLines()
        .subscribeOn(rxSchedulers.io())
        .map { lineList -> lineList.map { it.toDomainModel() } }

    override fun updateTubeStatus(lineList: List<Line>) =
        Observable.fromIterable(lineList).flatMapCompletable {
            lineDao.insertLine(it.fromDomainModel())
        }
}
