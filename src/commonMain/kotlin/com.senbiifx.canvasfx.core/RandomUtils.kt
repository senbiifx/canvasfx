package com.senbiifx.canvasfx.core

import kotlin.jvm.Synchronized
import kotlin.math.E
import kotlin.math.log
import kotlin.math.sqrt
import kotlin.random.Random

var nextNextGaussian = 0.0
var haveNextNextGaussian = false

@Synchronized
fun nextGaussian(): Double {
    // See Knuth, ACP, Section 3.4.1 Algorithm C.
    return if (haveNextNextGaussian) {
        haveNextNextGaussian = false
        nextNextGaussian
    } else {
        var v1: Double
        var v2: Double
        var s: Double
        do {
            v1 = 2 * Random.nextDouble() - 1 // between -1 and 1
            v2 = 2 * Random.nextDouble() - 1 // between -1 and 1
            s = v1 * v1 + v2 * v2
        } while (s >= 1 || s == 0.0)
        val multiplier: Double = sqrt(-2 * log(s, E) / s)
        nextNextGaussian = v2 * multiplier
        haveNextNextGaussian = true
        v1 * multiplier
    }
}