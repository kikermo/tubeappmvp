package org.kikermo.tube.persistence.di

import dagger.Binds
import dagger.Module
import org.kikermo.tube.persistence.TubeStore
import org.kikermo.tube.persistence.TubeStoreImpl
import javax.inject.Singleton

@Module
abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindTubeStore(tubeStoreImpl: TubeStoreImpl): TubeStore
}
