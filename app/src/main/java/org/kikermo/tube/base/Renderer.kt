package org.kikermo.tube.base

interface Renderer<VIEWSTATE : Any> {
    fun render(viewState: VIEWSTATE)
}
