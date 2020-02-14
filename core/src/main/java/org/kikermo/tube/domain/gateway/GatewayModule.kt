package org.kikermo.tube.domain.gateway

import dagger.Binds
import dagger.Module
import org.kikermo.tube.network.TubeGatewayImpl
import javax.inject.Singleton

@Module
abstract class GatewayModule {

    @Binds
    @Singleton
    abstract fun bindTubeGateway(tubeGatewayImpl: TubeGatewayImpl): TubeGateway
}
