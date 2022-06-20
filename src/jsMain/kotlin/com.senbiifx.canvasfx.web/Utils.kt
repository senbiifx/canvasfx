package com.senbiifx.canvasfx.web

import com.senbiifx.canvasfx.core.Color
import kotlin.math.round

internal fun coreColorToHtmlCanvasColor(color: Color): String{
    val r = round( color.getRed() * 255).toInt()
    val g = round(color.getGreen() * 255).toInt()
    val b = round(color.getBlue() * 255).toInt()
    val a = color.getOpacity()
    return "rgba(${r}, ${g}, ${b}, ${a})"
}