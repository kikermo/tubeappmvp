package org.kikermo.tube.tubelinestatuslist

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kikermo.tube.base.BasePresenter
import org.kikermo.tube.domain.TubeApi
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusRequest
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusResponse
import org.kikermo.tube.rx.RxSchedulers
import javax.inject.Inject

class TubeStateListPresenter @Inject constructor(
    private val tubeApi: TubeApi,
    private val rxSchedulers: RxSchedulers
) : BasePresenter<TubeStateListView> {

    private var view: TubeStateListView? = null

    private val compositeDisposable = CompositeDisposable()

    override fun subscribe() {
        Observable.just(GetTubeStatusRequest)
            .compose(tubeApi.getTubeStatus())
            .observeOn(rxSchedulers.main())
            .subscribe(this::handleResponse)
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

    private fun handleResponse(getTubeStatusResponse: GetTubeStatusResponse) {
        when (getTubeStatusResponse) {
            is GetTubeStatusResponse.LinesFetchedSuccess -> {
                view?.showLoading(false)
                view?.showError(false)
                view?.showData(getTubeStatusResponse.lines)
            }
            is GetTubeStatusResponse.Loading -> {
                view?.showLoading(true)
                view?.showError(false)
                getTubeStatusResponse.lines?.let { lines -> view?.showData(lines) }
            }
            is GetTubeStatusResponse.Error -> {
                view?.showLoading(false)
                view?.showError(true)
            }
        }
    }
}