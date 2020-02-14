package org.kikermo.tube.base

import android.content.Intent
import java.io.Serializable

interface NavigationAction : Serializable {
    companion object {
        const val EXTRAS_KEY = "org.kikermo.tube.base.NavigationAction.EXTRAS_KEY"
    }
}

interface ClearTaskNewTask

object BackNavigationAction : NavigationAction

fun <R : NavigationAction> Intent.getNavigationAction(): R? {
    return this.getSerializableExtra(NavigationAction.EXTRAS_KEY) as? R?
        ?: throw RuntimeException("The object should be castable")
}
