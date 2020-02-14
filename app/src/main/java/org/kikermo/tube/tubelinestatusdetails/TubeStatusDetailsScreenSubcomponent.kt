package org.kikermo.tube.tubelinestatusdetails

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [TubeStatusDetailsModule::class])
interface TubeStatusDetailsScreenSubcomponent : AndroidInjector<TubeStatusDetailsActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TubeStatusDetailsActivity>() {

        abstract override fun build(): TubeStatusDetailsScreenSubcomponent
    }
}
