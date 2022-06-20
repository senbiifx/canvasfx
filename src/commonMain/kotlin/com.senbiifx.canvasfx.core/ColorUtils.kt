package com.senbiifx.canvasfx.core

import kotlin.math.floor

internal fun RGBtoHSB(r: Double, g: Double, b: Double): DoubleArray {
    var hue: Double
    val saturation: Double
    val brightness: Double
    val hsbvals = DoubleArray(3)
    var cmax = if (r > g) r else g
    if (b > cmax) cmax = b
    var cmin = if (r < g) r else g
    if (b < cmin) cmin = b
    brightness = cmax
    saturation = if (cmax != 0.0) (cmax - cmin) / cmax else 0.0
    if (saturation == 0.0) {
        hue = 0.0
    } else {
        val redc = (cmax - r) / (cmax - cmin)
        val greenc = (cmax - g) / (cmax - cmin)
        val bluec = (cmax - b) / (cmax - cmin)
        hue = if (r == cmax) bluec - greenc else if (g == cmax) 2.0 + redc - bluec else 4.0 + greenc - redc
        hue = hue / 6.0
        if (hue < 0) hue = hue + 1.0
    }
    hsbvals[0] = hue * 360
    hsbvals[1] = saturation
    hsbvals[2] = brightness
    return hsbvals
}

internal fun HSBtoRGB(hue: Double, saturation: Double, brightness: Double): DoubleArray {
    // normalize the hue
    var hue = hue
    val normalizedHue = (hue % 360 + 360) % 360
    hue = normalizedHue / 360
    var r = 0.0
    var g = 0.0
    var b = 0.0
    if (saturation == 0.0) {
        b = brightness
        g = b
        r = g
    } else {
        val h = (hue - floor(hue)) * 6.0
        val f = h - floor(h)
        val p = brightness * (1.0 - saturation)
        val q = brightness * (1.0 - saturation * f)
        val t = brightness * (1.0 - saturation * (1.0 - f))
        when (h.toInt()) {
            0 -> {
                r = brightness
                g = t
                b = p
            }
            1 -> {
                r = q
                g = brightness
                b = p
            }
            2 -> {
                r = p
                g = brightness
                b = t
            }
            3 -> {
                r = p
                g = q
                b = brightness
            }
            4 -> {
                r = t
                g = p
                b = brightness
            }
            5 -> {
                r = brightness
                g = p
                b = q
            }
        }
    }
    val f = DoubleArray(3)
    f[0] = r
    f[1] = g
    f[2] = b
    return f
}