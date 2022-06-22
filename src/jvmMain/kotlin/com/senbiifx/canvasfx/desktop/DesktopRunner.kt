package com.senbiifx.canvasfx.desktop

import com.senbiifx.canvasfx.core.SketchConstructor
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage

private var canvasJavaFx: DesktopAnimationCanvas? = null

class DesktopRunner : Application(){

    override fun start(stage: Stage){
        val root = StackPane()
        canvasJavaFx?.let { root.children.add(canvasJavaFx as Canvas) }
        val scene = Scene(root)
        stage.title = "CanvasFX"
        stage.scene = scene
        stage.isResizable = false
        canvasJavaFx?.start()
        stage.show()
    }

}

fun show(w: Double, h: Double, sketchConstructor: SketchConstructor){
    canvasJavaFx = DesktopAnimationCanvas(sketchConstructor, w, h)
    Application.launch(DesktopRunner::class.java)
}

fun show(sketchConstructor: SketchConstructor){
    canvasJavaFx = DesktopAnimationCanvas(sketchConstructor, 640.0, 320.0)
    Application.launch(DesktopRunner::class.java)
}




