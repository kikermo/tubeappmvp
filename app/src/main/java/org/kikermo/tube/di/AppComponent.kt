package org.kikermo.tube.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import org.kikermo.tube.TubeStatusApp
import org.kikermo.tube.domain.ApiModule
import org.kikermo.tube.domain.gateway.GatewayModule
import org.kikermo.tube.network.di.NetworkModule
import org.kikermo.tube.network.di.ServicesModule
import org.kikermo.tube.persistence.di.DaoModule
import org.kikermo.tube.persistence.di.StoreModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        NavigationModule::class,
        StoreModule::class,
        DaoModule::class,
        ApiModule::class,
        GatewayModule::class,
        ServicesModule::class,
        ActivityBuilder::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: TubeStatusApp)
}
