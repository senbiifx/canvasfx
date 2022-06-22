package com.senbiifx.canvasfx.desktop

import com.senbiifx.canvasfx.core.*
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color as JavafxColor

class DesktopSketchContext(val g: GraphicsContext): SketchContext {

    override var fill: Color
        get() = javafxColorToCoreColor(g.fill as JavafxColor)
        set(value) { g.fill = coreColorToJavaFxColor(value) }

    override var stroke: Color
        get() = javafxColorToCoreColor(g.stroke as JavafxColor)
        set(value) { g.stroke = coreColorToJavaFxColor(value) }

    override val pixelWriter: PixelWriter = DesktopPixelWriter(g.pixelWriter)

    private fun javafxColorToCoreColor(javafxColor: JavafxColor) =
                Color(javafxColor.red, javafxColor.green, javafxColor.blue, javafxColor.opacity)

    private fun coreColorToJavaFxColor(color: Color) =
                JavafxColor(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity())

    override fun fillRect(var1: Double, var2: Double, var3: Double, var4: Double) {
        g.fillRect(var1, var2, var3, var4)
    }

    override fun fillOval(x: Double, y: Double, w: Double, h: Double) {
        g.fillOval(x, y, w, h)
    }

    override fun save() {
        g.save()
    }

    override fun restore() {
        g.restore()
    }

    override fun fillText(var1: String, var2: Double, var4: Double) {
        g.fillText(var1, var2, var4)
    }

    override fun strokeLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        g.strokeLine(x1, y1, x2, y2)
    }

    override fun translate(x: Double, y: Double) {
        g.translate(x, y)
    }

    override fun rotate(degrees: Double) {
        g.rotate(degrees)
    }

    override fun scale(x: Double, y: Double) {
        g.scale(x, y)
    }

    override fun createGraphics(width: Double, height: Double, sketchFunction: SketchFunction): WritableImage {
        val canvas = Canvas(width, height)
        sketchFunction.invoke(DesktopSketchContext(canvas.graphicsContext2D))
        return DesktopWritableImage(canvas.snapshot(null, null))
    }

    override fun drawImage(image: WritableImage, x: Double, y: Double) {
        if(image is DesktopWritableImage){
            g.drawImage(image.writableImage, x, y)
        }
    }

}