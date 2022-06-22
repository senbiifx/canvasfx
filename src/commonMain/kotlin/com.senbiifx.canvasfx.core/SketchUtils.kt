package com.senbiifx.canvasfx.core

import kotlin.jvm.Synchronized
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.log
import kotlin.math.sqrt
import kotlin.random.Random

fun noise(x: Double) = Noise.noise(x, 0.0, 0.0)
fun noise(x: Double, y: Double) = Noise.noise(x, y, 0.0)
fun noise(x: Double, y: Double, z: Double) = Noise.noise(x, y, z)


fun map(value: Double, istart: Double, istop: Double, ostart: Double, ostop: Double ) =
                            ostart + (ostop - ostart) * ((value - istart) / (istop - istart))

fun random(until: Int): Int = Random.nextInt(until)
fun random(from: Int, until: Int): Int = Random.nextInt(from, until)

fun Double.toRadians() = this / 180 * PI
fun Int.toRadians() = this.toDouble().toRadians()
fun Double.toDegrees() = this * 180 / PI
fun Int.toDegrees() = this.toDouble().toDegrees()

fun randomGaussian(mean: Double, sd: Double = 1.0) = nextGaussian() * mean + sd
