package org.kikermo.tube.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

abstract class BaseMVIActivity<VIEWSTATE : Any, VIEWMODEL : BaseViewModel<VIEWSTATE>> : AppCompatActivity(),
    Renderer<VIEWSTATE> {

    protected abstract val activityLayout: Int

    protected abstract val actions: Observable<ScreenAction>

    @Inject lateinit var viewModel: VIEWMODEL
    @Inject lateinit var navigationDelegate: NavigationDelegate

    private val compositeDisposable = CompositeDisposable()

    private val lifecycleBehaviorSubject: BehaviorSubject<LifecycleEvent> = BehaviorSubject.createDefault(LifecycleEvent.Created)

    protected val lifecycleObservable: Observable<LifecycleEvent>
        get() = lifecycleBehaviorSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityLayout.run { setContentView(this) }
        viewModel.bindView(actions.mergeWith(lifecycleObservable), this, navigationDelegate)
        lifecycleBehaviorSubject.onNext(LifecycleEvent.Created)
    }

    override fun onStart() {
        super.onStart()
        lifecycleBehaviorSubject.onNext(LifecycleEvent.Started)
    }

    override fun onResume() {
        super.onResume()
        lifecycleBehaviorSubject.onNext(LifecycleEvent.Resumed)
    }

    override fun onPause() {
        super.onPause()
        lifecycleBehaviorSubject.onNext(LifecycleEvent.Paused)
    }

    override fun onStop() {
        super.onStop()
        lifecycleBehaviorSubject.onNext(LifecycleEvent.Stopped)
    }

    override fun onDestroy() {
        lifecycleBehaviorSubject.onNext(LifecycleEvent.Destroyed)
        viewModel.unbind()
        compositeDisposable.dispose()
        super.onDestroy()
    }

    fun Disposable.registerDisposable() {
        compositeDisposable.add(this)
    }
}
