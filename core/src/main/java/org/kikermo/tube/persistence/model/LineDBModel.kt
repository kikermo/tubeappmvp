package org.kikermo.tube.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.domain.model.LineStatus

@Entity(tableName = "lines")
data class LineDBModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "severity")
    val severity: Int = 0,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "reason")
    val reason: String? = null
)

fun Line.fromDomainModel() = LineDBModel(
    id = id,
    name = name,
    severity = status?.severity ?: 0,
    reason = status?.reason,
    description = status?.description
)

fun LineDBModel.toDomainModel() = Line(
    id = id,
    name = name,
    status = description?.let { LineStatus(severity, description, reason) }
)
