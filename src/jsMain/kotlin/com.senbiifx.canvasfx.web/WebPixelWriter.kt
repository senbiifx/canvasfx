package com.senbiifx.canvasfx.web

import com.senbiifx.canvasfx.core.Color
import com.senbiifx.canvasfx.core.PixelWriter
import org.w3c.dom.CanvasRenderingContext2D

class WebPixelWriter(val ctx: CanvasRenderingContext2D): PixelWriter {
    override fun setColor(x: Int, y: Int, color: Color) {
        ctx.save()
        ctx.fillStyle = coreColorToHtmlCanvasColor(color)
        ctx.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
        ctx.restore()
    }
}