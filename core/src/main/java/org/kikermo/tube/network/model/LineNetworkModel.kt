package org.kikermo.tube.network.model

import com.google.gson.annotations.SerializedName
import org.kikermo.tube.domain.model.Line
import java.util.*

data class LineNetworkModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("lineStatuses")
    val lineStatuses: List<LineStatusNetworkModel>,

    @SerializedName("created")
    val created: Date,

    @SerializedName("modified")
    val modified: Date

)

internal fun LineNetworkModel.toDomainEntity() = Line(
    id = id,
    name = name,
    status = lineStatuses.firstOrNull()?.toDomainModel(),
    lastUpdated = modified
)
