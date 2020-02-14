package org.kikermo.tube.tubelinestatuslist

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kikermo.tube.base.BasePresenter
import org.kikermo.tube.domain.TubeApi
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusRequest
import javax.inject.Inject

class TubeStateListPresenter @Inject constructor(
    private val tubeApi: TubeApi
) : BasePresenter<TubeStateListView> {

    private var view: TubeStateListView? = null

    private val compositeDisposable = CompositeDisposable()

    override fun subscribe() {
        Observable.just(GetTubeStatusRequest)
            .compose(tubeApi.getTubeStatus())
            .subscribe()
            .registerDisposable()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun attachView(view: TubeStateListView?) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    private fun Disposable.registerDisposable() {
        compositeDisposable.add(this)
    }
}