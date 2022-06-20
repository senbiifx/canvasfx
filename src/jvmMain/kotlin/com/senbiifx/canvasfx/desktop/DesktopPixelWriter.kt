package com.senbiifx.canvasfx.desktop

import com.senbiifx.canvasfx.core.Color
import com.senbiifx.canvasfx.core.PixelWriter
import javafx.scene.image.PixelWriter as JavaFxPixelWriter
import javafx.scene.paint.Color as JavaFxColor

class DesktopPixelWriter(val pixelWriter: JavaFxPixelWriter): PixelWriter {

    override fun setColor(x: Int, y: Int, color: Color) {
        pixelWriter.setColor(x, y, JavaFxColor(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity()))
    }
}