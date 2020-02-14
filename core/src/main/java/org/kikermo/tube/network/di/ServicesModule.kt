package org.kikermo.tube.network.di

import dagger.Module
import dagger.Provides
import org.kikermo.tube.network.service.TubeService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServicesModule {

    @Provides
    @Singleton
    fun providesTubeService(retrofit: Retrofit): TubeService = retrofit.create(TubeService::class.java)
}
