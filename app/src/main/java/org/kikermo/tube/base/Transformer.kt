package org.kikermo.tube.base

import io.reactivex.ObservableTransformer

interface Transformer<VIEWSTATE : Any> {

    fun getTransformers(): List<ObservableTransformer<FlowEvent<ScreenAction, VIEWSTATE>, Any>>
}
