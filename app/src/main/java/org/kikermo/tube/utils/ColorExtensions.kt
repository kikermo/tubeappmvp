package org.kikermo.tube.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import org.kikermo.tube.domain.model.Line

@SuppressWarnings("MagicNumber")
fun Line.toLineColour() = when (this.id) {
    "bakerloo" -> Color.rgb(178, 99, 0)
    "central" -> Color.rgb(220, 36, 31)
    "circle" -> Color.rgb(255, 211, 41)
    "district" -> Color.rgb(0, 125, 50)
    "hammersmith-city" -> Color.rgb(244, 169, 190)
    "jubilee" -> Color.rgb(161, 165, 167)
    "metropolitan" -> Color.rgb(155, 0, 88)
    "northern" -> Color.rgb(0, 0, 0)
    "piccadilly" -> Color.rgb(0, 25, 168)
    "victoria" -> Color.rgb(0, 152, 216)
    "waterloo-city" -> Color.rgb(147, 206, 186)
    else -> Color.BLACK
}

@SuppressWarnings("MagicNumber")
fun Line.toTranslucidColour() = ColorUtils.setAlphaComponent(toLineColour(),40)


// Totally speculative, could't find severity meaning on documentation
@SuppressWarnings("MagicNumber")
fun Line.toSeverityColour() = when (this.status?.severity) {
    in 1..4 -> Color.RED
    in 5..9 -> Color.YELLOW
    in 10..12 -> Color.rgb(102,153,0)
    else -> Color.TRANSPARENT
}
