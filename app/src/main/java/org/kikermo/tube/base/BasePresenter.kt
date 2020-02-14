package org.kikermo.tube.base

interface BasePresenter<T : BaseView> {
    fun subscribe()

    fun unsubscribe()

    fun attachView(view: T?)

    fun detachView()
}