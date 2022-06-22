package com.senbiifx.canvasfx.core


interface SketchContext {
    var fill: Color
    var stroke: Color
    val pixelWriter: PixelWriter
    fun fillRect(x: Double, y: Double, w: Double, h: Double)
    fun fillOval(x: Double, y: Double, w: Double, h: Double)
    fun save()
    fun restore()
    fun fillText(string: String, x: Double, y: Double)
    fun strokeLine(x1: Double, y1: Double, x2: Double, y2: Double)
    fun translate(x: Double, y: Double)
    fun rotate(angle: Double)

    fun scale(x: Double, y: Double)

    fun point(x: Double, y: Double) = fillRect(x, y, 1.0, 1.0)

    fun matrix(action: () -> Unit){
        save()
        action.invoke()
        restore()
    }

    fun createGraphics(width: Double, height: Double, sketchable: SketchFunction): WritableImage

    fun drawImage(image: WritableImage, x: Double, y: Double)
}