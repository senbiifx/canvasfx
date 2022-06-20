package com.senbiifx.canvasfx.desktop

import com.senbiifx.canvasfx.core.Sketch
import com.senbiifx.canvasfx.core.SketchConstructor
import javafx.animation.Animation
import javafx.animation.AnimationTimer
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.*
import javafx.event.ActionEvent
import javafx.scene.canvas.Canvas
import javafx.scene.input.MouseEvent
import javafx.scene.input.MouseEvent.*
import javafx.util.Duration


class DesktopAnimationCanvas(val sketch: Sketch): Canvas() {

    private val frameCountProperty: LongProperty = SimpleLongProperty(0L)
    private val nanoProperty: LongProperty = SimpleLongProperty(0L)
    private val mouseXProperty: DoubleProperty = SimpleDoubleProperty(0.0)
    private var mouseYProperty: DoubleProperty = SimpleDoubleProperty(0.0)
    private var mousePressedProperty: BooleanProperty = SimpleBooleanProperty(false)
    private var startNano = 0L
    private var sketchContext  = DesktopSketchContext(graphicsContext2D)
    private val defaultFps = 60

    val timeline = Timeline(KeyFrame(Duration.seconds(fps(defaultFps)), loop()))


    constructor(sketchConstructor: SketchConstructor, w: Double, h: Double): this(sketchConstructor.invoke(w, h)) {
        width = w
        height = h
        isFocused = true
        isFocusTraversable = true

    }

    init {
        frameCountProperty.addListener { var1, var2, var3 -> sketch.frameCount = frameCountProperty.get() }
        nanoProperty.addListener { var1, var2, var3 -> sketch.nano = nanoProperty.get() }
        widthProperty().addListener { var1, var2, var3 -> sketch.width = widthProperty().get() }
        heightProperty().addListener { var1, var2, var3 -> sketch.height = heightProperty().get() }
        mouseXProperty.addListener { var1, var2, var3 -> sketch.mouseX = mouseXProperty.get() }
        mouseYProperty.addListener { var1, var2, var3 -> sketch.mouseY = mouseYProperty.get() }
        mousePressedProperty.addListener { var1, var2, var3 -> sketch.mousePressed = mousePressedProperty.get() }

        addEventFilter(MouseEvent.ANY) {
            mouseXProperty.set(it.x)
            mouseYProperty.set(it.y)

            when (it.eventType) {
                MOUSE_PRESSED -> {
                    mousePressedProperty.set(true)
                }
                MOUSE_RELEASED -> {
                    mousePressedProperty.set(false)
                }
            }
        }

        timeline.cycleCount = Animation.INDEFINITE
    }



    val timer = object : AnimationTimer() {
        override fun handle(now: Long) {
            nanoProperty.set(now - startNano)
            frameCountProperty.set(frameCountProperty.get() + 1)
            sketch.draw(sketchContext)
        }
    }

    fun start() {
        startNano = System.nanoTime()
        sketch.setup(sketchContext)
        timeline.play()
    }

    fun stop() {
        timeline.stop()
    }

    private fun fps(num: Int) = 1.0 / num

    private fun loop(): (event: ActionEvent) -> Unit = {
        frameCountProperty.set(frameCountProperty.get() + 1)
        sketch.draw(sketchContext)
    }
}


