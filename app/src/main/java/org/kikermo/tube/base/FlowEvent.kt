package org.kikermo.tube.base

data class FlowEvent<ACTION : ScreenAction, STATE : Any>(val action: ACTION, val state: STATE)
