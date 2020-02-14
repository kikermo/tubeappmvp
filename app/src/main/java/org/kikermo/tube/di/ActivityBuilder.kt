package org.kikermo.tube.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import org.kikermo.tube.tubelinestatusdetails.TubeStatusDetailsActivity
import org.kikermo.tube.tubelinestatusdetails.TubeStatusDetailsScreenSubcomponent
import org.kikermo.tube.tubelinestatuslist.TubeStateListActivity
import org.kikermo.tube.tubelinestatuslist.TubeStatusScreenSubcomponent

@Module(subcomponents = [
    TubeStatusScreenSubcomponent::class,
    TubeStatusDetailsScreenSubcomponent::class
])
abstract class ActivityBuilder {
    @Binds
    @IntoMap
    @ActivityKey(TubeStateListActivity::class)
    abstract fun bindTubeStateListActivity(builder: TubeStatusScreenSubcomponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(TubeStatusDetailsActivity::class)
    abstract fun bindTubeDetailsActivity(builder: TubeStatusDetailsScreenSubcomponent.Builder): AndroidInjector.Factory<out Activity>
}
