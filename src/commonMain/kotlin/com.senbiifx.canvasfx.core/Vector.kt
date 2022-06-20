package com.senbiifx.canvasfx.core

import kotlin.math.sqrt

data class Vector(val x: Double, val y: Double) {
    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)
    operator fun plus(n: Double) = Vector(x + n, y + n)
    operator fun minus(v: Vector) = Vector(x - v.x, y - v.y)
    operator fun minus(n: Double) = Vector(x - n, y - n)
    operator fun times(v: Vector) = Vector(x * v.x, y * v.y)
    operator fun times(n: Double) = Vector(x * n, y * n)
    operator fun div(v: Vector) = Vector(x / v.x, y / v.y)
    operator fun div(n: Double) = Vector(x / n, y / n)

    fun distance(v: Vector) =  minus(v).magnitude()

    fun dist(v: Vector): Double {
        val dx: Double = x - v.x
        val dy: Double = y - v.y
        return sqrt(dx * dx + dy * dy)
    }

    fun normalize(): Vector {
        val m = magnitude()
        return if (m != 0.0 && m != 1.0) div(m) else get()
    }

    fun limit(max: Double) = if (magnitude() > max) normalize().times(max) else get()

    fun magnitude() = sqrt(x * x + y * y)

    fun get() = Vector(x, y)
}
