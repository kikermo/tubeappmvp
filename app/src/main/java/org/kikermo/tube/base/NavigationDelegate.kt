package org.kikermo.tube.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class NavigationDelegate @Inject constructor(
    private val activity: AppCompatActivity,
    private val simpleIntentTargets: Map<Class<out NavigationAction>, @JvmSuppressWildcards Class<*>>
) {
    fun navigate(navigationAction: NavigationAction) {

        if (navigationAction is BackNavigationAction) {
            activity.finish()
            return
        }

        val intent = simpleIntentTargets[navigationAction::class.java]?.let {
            Intent(activity, it)
        } ?: throw IllegalStateException("No target bound for action ${navigationAction.javaClass.name}!")

        intent.putExtra(NavigationAction.EXTRAS_KEY, navigationAction)

        if (navigationAction is ClearTaskNewTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }

        activity.startActivity(intent)
    }
}
