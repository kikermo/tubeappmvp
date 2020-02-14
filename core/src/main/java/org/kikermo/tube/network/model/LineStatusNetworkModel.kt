package org.kikermo.tube.network.model

import com.google.gson.annotations.SerializedName
import org.kikermo.tube.domain.model.LineStatus

data class LineStatusNetworkModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("statusSeverity")
    val statusSeverity: Int,

    @SerializedName("statusSeverityDescription")
    val statusSeverityDescription: String,

    @SerializedName("reason")
    val reason: String? = null
)

internal fun LineStatusNetworkModel.toDomainModel() = LineStatus(
    severity = statusSeverity,
    description = statusSeverityDescription,
    reason = reason
)
