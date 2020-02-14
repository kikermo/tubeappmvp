package org.kikermo.tube.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.kikermo.tube.rx.RxSchedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseViewModel<VIEWSTATE : Any>
constructor(initialViewState: VIEWSTATE) : ViewModel() {

    @Inject
    lateinit var reducer: Reducer<VIEWSTATE>
    @Inject
    lateinit var transformer: Transformer<VIEWSTATE>
    @Inject
    lateinit var rxSchedulers: RxSchedulers
    @Inject
    lateinit var navigationRelay: Subject<NavigationAction>

    private var navigationDelegate: NavigationDelegate? = null
    private val viewStateBehaviourSubject: BehaviorSubject<VIEWSTATE> = BehaviorSubject.createDefault(initialViewState)
    private val compositeDisposable = CompositeDisposable()

    private val viewStateObservable: Observable<VIEWSTATE>
        get() = viewStateBehaviourSubject

    fun bindView(actions: Observable<ScreenAction>, renderer: Renderer<VIEWSTATE>, navigationDelegate: NavigationDelegate) {
        this.navigationDelegate = navigationDelegate
        val sharedActions = actions.share()
        Observable.merge(
                transformer.getTransformers().map {
                    sharedActions.map { sharedAction -> FlowEvent(sharedAction, viewStateBehaviourSubject.value) }
                            .compose(it)
                            .observeOn(rxSchedulers.io())
                })
                .zipWith(
                        viewStateObservable,
                        BiFunction<Any, VIEWSTATE, VIEWSTATE> { event, viewState ->
                            reducer.reduce(event = event, currentState = viewState)
                        })
                .subscribe({
                    viewStateBehaviourSubject.onNext(it)
                }, { throwable -> Timber.e(throwable, "Critical problem happened on BaseViewModel") })
                .registerDisposable()

        viewStateObservable
                .takeUntil(sharedActions.filter { it == LifecycleEvent.Destroyed })
                .observeOn(rxSchedulers.main())
                .subscribe({
                    renderer.render(it)
                }, { throwable -> Timber.e(throwable, "Critical render problem happened on BaseViewModel") })
                .registerDisposable()

        navigationRelay
            .throttleFirst(DEBOUNCE_SECONDS, TimeUnit.SECONDS)
            .observeOn(rxSchedulers.main())
            .subscribe({ this.navigationDelegate?.navigate(it) },
                { throwable ->
                    Timber.e(
                        throwable,
                        "Critical navigation problem happened on BaseViewModel"
                    )
                })
            .registerDisposable()
    }

    fun unbind() {
        navigationDelegate = null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun Disposable.registerDisposable() {
        compositeDisposable.add(this)
    }

    companion object {
        private const val DEBOUNCE_SECONDS = 1L
    }
}
