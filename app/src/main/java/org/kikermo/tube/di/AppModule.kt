package org.kikermo.tube.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.kikermo.tube.TubeStatusApp
import org.kikermo.tube.rx.RxSchedulers
import org.kikermo.tube.rx.RxSchedulersImpl
import javax.inject.Singleton

@Module
class AppModule(val app: TubeStatusApp) {
    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun providesRxSchedulers(): RxSchedulers = RxSchedulersImpl()
}
