package com.senbiifx.canvasfx.desktop

import com.senbiifx.canvasfx.core.SketchConstructor
import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Screen
import javafx.stage.Stage

private var windowInitializer: ((Stage) -> Unit)? = null

class DesktopRunner : Application(){

    override fun start(stage: Stage){
        windowInitializer?.let { it.invoke(stage) }
    }

}

fun show(w: Double, h: Double, sketchConstructor: SketchConstructor){
    windowInitializer = { stage ->
        val root = StackPane()
        val canvasJavaFx = DesktopAnimationCanvas(sketchConstructor, w, h)
        root.children.add(canvasJavaFx as Canvas)
        val scene = Scene(root)
        stage.title = "CanvasFX"
        stage.scene = scene
        stage.isResizable = false
        canvasJavaFx.start()
        stage.show()
    }

    Application.launch(DesktopRunner::class.java)
}

fun show(sketchConstructor: SketchConstructor){
    windowInitializer =
        { stage ->
                val root = StackPane()
                val scene = Scene(root)
                stage.title = "CanvasFX"
                stage.scene = scene
                stage.isResizable = false
                stage.isFullScreen = true

                val screen: Screen = Screen.getPrimary()
                val bounds: Rectangle2D = screen.bounds
                val canvasJavaFx = DesktopAnimationCanvas(sketchConstructor, bounds.width, bounds.height)
                root.children.add(canvasJavaFx)
                canvasJavaFx.start()
                stage.show()

        }
    Application.launch(DesktopRunner::class.java)
}




