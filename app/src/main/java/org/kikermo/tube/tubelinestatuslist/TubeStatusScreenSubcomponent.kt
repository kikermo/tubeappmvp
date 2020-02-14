package org.kikermo.tube.tubelinestatuslist

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [TubeStatusListModule::class])
interface TubeStatusScreenSubcomponent : AndroidInjector<TubeStateListActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TubeStateListActivity>() {

        abstract override fun build(): TubeStatusScreenSubcomponent
    }
}
