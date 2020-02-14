package org.kikermo.tube.base

sealed class LifecycleEvent : ScreenAction() {
    object Created : LifecycleEvent()
    object Started : LifecycleEvent()
    object Resumed : LifecycleEvent()
    object Paused : LifecycleEvent()
    object Stopped : LifecycleEvent()
    object Destroyed : LifecycleEvent()
}
