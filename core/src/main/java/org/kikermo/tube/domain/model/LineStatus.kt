package org.kikermo.tube.domain.model

data class LineStatus(
    val severity: Int,
    val description: String,
    val reason: String? = null
)
