package com.senbiifx.canvasfx.core


class Sketch(var width: Double, var height: Double, f: Sketch.() -> Unit) {

    var frameCount = 0L
    var nano = 0L
    var mouseX = 0.0
    var mouseY = 0.0
    var mousePressed = false


    private var draw: (SketchFunction) = {}
    private var setup: (SketchFunction) = {}

    init {
        f()
    }

    fun draw(f: SketchFunction){
        draw = f
    }

    fun setup(f: SketchFunction){
        setup = f
    }

    fun draw(f: SketchContext){
        draw.invoke(f)
    }

    fun setup(f: SketchContext){
        setup.invoke(f)
    }

}

typealias SketchConstructor = (width: Double, height: Double) -> Sketch

fun sketch(f: Sketch.() -> Unit): SketchConstructor = {
    w, h -> Sketch(w, h, f)
}
