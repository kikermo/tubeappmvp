package org.kikermo.tube

import android.app.Activity
import android.app.Application
import org.kikermo.tube.di.AppModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.kikermo.tube.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class TubeStatusApp : Application(), HasActivityInjector {

    // region DI
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityInjector

    //endregion

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent
            .builder()
            .application(this)
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }
}
