package com.senbiifx.canvasfx.web

import com.senbiifx.canvasfx.core.Sketch
import com.senbiifx.canvasfx.core.SketchConstructor
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.round
import kotlin.properties.Delegates

class WebAnimationCanvas(val sketch: Sketch) {
    private var startNano = 0L
    private var frameCount: Long
        by Delegates.observable(0L) {
                property, oldValue, newValue ->
            sketch.frameCount = newValue
        }

    //TODO: calculation
    private var nano: Long
            by Delegates.observable(0L) {
                    property, oldValue, newValue ->
                sketch.nano = newValue
            }
    private var mouseX: Double
            by Delegates.observable(0.0) {
                    property, oldValue, newValue ->
                sketch.mouseX = newValue
            }
    private var mouseY: Double
            by Delegates.observable(0.0) {
                    property, oldValue, newValue ->
                sketch.mouseY = newValue
            }
    private var mousePressed: Boolean
            by Delegates.observable(false) {
                    property, oldValue, newValue ->
                sketch.mousePressed = newValue
            }
    private lateinit var sketchContext: WebSketchContext
    lateinit var canvas: HTMLCanvasElement

    constructor(sketchConstructor: SketchConstructor, w: Double, h: Double): this(sketchConstructor.invoke(w, h)) {
        canvas =  document.createElement("canvas") as HTMLCanvasElement
        canvas.width = w.toInt()
        canvas.height = h.toInt()

        val ctx = canvas.getContext("2d") as CanvasRenderingContext2D
        sketchContext = WebSketchContext(ctx)

        canvas.addEventListener("onmousemove", {
            val pos = it.currentTarget.asDynamic().getBoundingClientRect()
            mouseX = it.asDynamic().clientX() - pos.left
            mouseY = it.asDynamic().clientY() - pos.top
        })

        canvas.addEventListener("onmousedown", {
            mousePressed = true
        })

        canvas.addEventListener("onmouseup", {
            mousePressed = false
        })

        
    }


    fun start() {
        sketch.setup(sketchContext)
        window.setInterval( {
            frameCount += 1
            sketch.draw(sketchContext)

                            }, fps(60))
    }

    fun drawLoop(){
        frameCount += 1
        sketch.draw(sketchContext)

    }


    fun stop() {
        window.clearInterval()
    }

    private fun fps(num: Int) = round(1000.0 / num.toDouble()).toInt()
}