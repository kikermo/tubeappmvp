package org.kikermo.tube.domain

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApiModule {

    @Binds
    @Singleton
    abstract fun bindTubeApi(tubeApiImpl: TubeApiImpl): TubeApi
}
