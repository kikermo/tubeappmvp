package org.kikermo.tube.network.service

import io.reactivex.Single
import org.kikermo.tube.network.model.LineNetworkModel
import retrofit2.http.GET

interface TubeService {

    @GET("line/mode/tube/status")
    fun getTubeStatus(): Single<List<LineNetworkModel>>
}
