package org.kikermo.tube.base

interface Reducer<VIEWSTATE : Any> {

    fun reduce(currentState: VIEWSTATE, event: Any): VIEWSTATE
}
