package com.senbiifx.canvasfx.core

import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

internal object Noise {
    private const val PERLIN_YWRAPB = 4
    private const val PERLIN_YWRAP = 1 shl PERLIN_YWRAPB
    private const val PERLIN_ZWRAPB = 8
    private const val PERLIN_ZWRAP = 1 shl PERLIN_ZWRAPB
    private const val PERLIN_SIZE = 4095
    private const val perlin_octaves = 4
    private const val perlin_amp_falloff = 0.5f
    private var perlin_TWOPI = 0
    private var perlin_PI = 0
    private var perlin_cosTable: DoubleArray? = null
    private var perlin: DoubleArray? = null
    private val sinLUT: DoubleArray
    private val cosLUT: DoubleArray
    private const val SINCOS_PRECISION = 0.5
    private const val SINCOS_LENGTH = (360f / SINCOS_PRECISION).toInt()
    private const val DEG_TO_RAD = 0.017453292519943295769236907684886
    private val perlinRandom = Random
    fun noise(x: Double, y: Double, z: Double): Double {
        var x = x
        var y = y
        var z = z
        if (perlin == null) {
            perlin = DoubleArray(PERLIN_SIZE + 1)
            for (i in 0 until PERLIN_SIZE + 1) {
                perlin!![i] = perlinRandom.nextDouble()
            }
            perlin_cosTable = cosLUT
            perlin_PI = SINCOS_LENGTH
            perlin_TWOPI = perlin_PI
            perlin_PI = perlin_PI shr 1
        }
        if (x < 0) x = -x
        if (y < 0) y = -y
        if (z < 0) z = -z
        var xi = x.toInt()
        var yi = y.toInt()
        var zi = z.toInt()
        var xf = x - xi
        var yf = y - yi
        var zf = z - zi
        var rxf: Double
        var ryf: Double
        var r = 0.0
        var ampl = 0.5
        var n1: Double
        var n2: Double
        var n3: Double
        for (i in 0 until perlin_octaves) {
            var of = xi + (yi shl PERLIN_YWRAPB) + (zi shl PERLIN_ZWRAPB)
            rxf = noise_fsc(xf)
            ryf = noise_fsc(yf)
            n1 = perlin!![of and PERLIN_SIZE]
            n1 += rxf * (perlin!![of + 1 and PERLIN_SIZE] - n1)
            n2 = perlin!![of + PERLIN_YWRAP and PERLIN_SIZE]
            n2 += rxf * (perlin!![of + PERLIN_YWRAP + 1 and PERLIN_SIZE] - n2)
            n1 += ryf * (n2 - n1)
            of += PERLIN_ZWRAP
            n2 = perlin!![of and PERLIN_SIZE]
            n2 += rxf * (perlin!![of + 1 and PERLIN_SIZE] - n2)
            n3 = perlin!![of + PERLIN_YWRAP and PERLIN_SIZE]
            n3 += rxf * (perlin!![of + PERLIN_YWRAP + 1 and PERLIN_SIZE] - n3)
            n2 += ryf * (n3 - n2)
            n1 += noise_fsc(zf) * (n2 - n1)
            r += n1 * ampl
            ampl *= perlin_amp_falloff.toDouble()
            xi = xi shl 1
            xf *= 2.0
            yi = yi shl 1
            yf *= 2.0
            zi = zi shl 1
            zf *= 2.0
            if (xf >= 1.0f) {
                xi++
                xf--
            }
            if (yf >= 1.0f) {
                yi++
                yf--
            }
            if (zf >= 1.0f) {
                zi++
                zf--
            }
        }
        return r
    }

    private fun noise_fsc(i: Double): Double {
        return 0.5f * (1.0f - perlin_cosTable!![(i * perlin_PI).toInt() % perlin_TWOPI])
    }

    init {
        sinLUT = DoubleArray(SINCOS_LENGTH)
        cosLUT = DoubleArray(SINCOS_LENGTH)
        for (i in 0 until SINCOS_LENGTH) {
            sinLUT[i] = sin(i * DEG_TO_RAD * SINCOS_PRECISION)
            cosLUT[i] = cos(i * DEG_TO_RAD * SINCOS_PRECISION)
        }
    }
}