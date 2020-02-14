package org.kikermo.tube.domain.model

import java.util.*

data class Line(
    val id: String,
    val name: String,
    val status: LineStatus?,
    val lastUpdated: Date? = null
)
