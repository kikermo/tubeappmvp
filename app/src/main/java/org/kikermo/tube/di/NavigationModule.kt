package org.kikermo.tube.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.kikermo.tube.base.NavigationAction
import org.kikermo.tube.base.NavigationActionKey
import org.kikermo.tube.navigation.TubeStatusDetailsNavigationAction
import org.kikermo.tube.tubelinestatusdetails.TubeStatusDetailsActivity
import javax.inject.Singleton

@Module
class NavigationModule {

    @Singleton
    @Provides
    fun providesNavigationRelay(): Subject<NavigationAction> =
        PublishSubject.create<NavigationAction>()

    @Provides
    @IntoMap
    @NavigationActionKey(TubeStatusDetailsNavigationAction::class)
    fun bindProductsTarget(): Class<*> = TubeStatusDetailsActivity::class.java
}
