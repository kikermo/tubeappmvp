package org.kikermo.tube.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseMVPActivity<V : BaseView, P : BasePresenter<V>> : AppCompatActivity() {

    abstract fun providePresenter(): P
    abstract fun provideView(): V

    protected val presenter by lazy { providePresenter() }


    override fun onStart() {
        super.onStart()
        presenter.attachView(provideView())
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
        presenter.detachView()
    }
}
