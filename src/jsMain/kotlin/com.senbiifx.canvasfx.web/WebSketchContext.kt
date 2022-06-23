package com.senbiifx.canvasfx.web

import com.senbiifx.canvasfx.core.*
import kotlinx.browser.document
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.PI
import kotlin.math.round

class WebSketchContext(val ctx: CanvasRenderingContext2D): SketchContext {
    private var color = Color.BLACK

    override var fill: Color
        get() = color
        set(value) {
            color = value
            ctx.fillStyle = coreColorToHtmlCanvasColor(value)
        }

    override var stroke: Color
        get() = color
        set(value) {
            color = value
            ctx.strokeStyle = coreColorToHtmlCanvasColor(value)
        }

    override val pixelWriter: PixelWriter = WebPixelWriter(ctx)

    override fun fillRect(var1: Double, var2: Double, var3: Double, var4: Double) {
        ctx.fillRect(var1, var2, var3, var4)
    }

    override fun fillOval(x: Double, y: Double, w: Double, h: Double) {
        ctx.save()
        ctx.beginPath()

        ctx.translate(x-w/2, y-h/2)
        ctx.scale(w, h)
        ctx.arc(1.0, 1.0, 0.5, 0.0, 2 * PI, false)

        ctx.restore()
        ctx.fill()
    }

    override fun save() {
        ctx.save()
    }

    override fun restore() {
        ctx.restore()
    }

    override fun fillText(var1: String, var2: Double, var3: Double) {
        ctx.fillText(var1, var2, var3)
    }

    override fun strokeLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        ctx.save()
        ctx.moveTo(x1, y1)
        ctx.lineTo(x2, y2)
        ctx.stroke()
        ctx.restore()
    }

    override fun translate(x: Double, y: Double) {
        ctx.translate(x, y)
    }

    override fun rotate(angle: Double) {
        ctx.rotate(angle.toRadians())
    }

    override fun scale(x: Double, y: Double) {
        ctx.scale(x, y)
    }

    override fun beginPath() {
        ctx.beginPath()
    }

    override fun closePath() {
        ctx.closePath()
    }

    override fun bezierCurveTo(xc1: Double, yc1: Double, xc2: Double, yc2: Double, x1: Double, y1: Double) {
        ctx.bezierCurveTo(xc1, yc1, xc2, yc2, x1, y1)
    }

    override fun createGraphics(width: Double, height: Double, sketchFunction: SketchFunction): WritableImage {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        canvas.width = width.toInt()
        canvas.height = height.toInt()
        val ctx = canvas.getContext("2d") as CanvasRenderingContext2D
        sketchFunction.invoke(WebSketchContext(ctx))
        return WebWritableImage(canvas)
    }

    override fun drawImage(image: WritableImage, x: Double, y: Double) {
        if(image is WebWritableImage){
            ctx.drawImage(image.canvas, x, y)
        }
    }

}