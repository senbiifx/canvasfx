package com.senbiifx.canvasfx.web

import com.senbiifx.canvasfx.core.SketchConstructor
import kotlinx.browser.document
import kotlinx.browser.window


fun show(sketchConstructor: SketchConstructor, w: Double = window.innerWidth.toDouble(), h: Double = window.innerHeight.toDouble(), parentId: String? = null){
    val c = WebAnimationCanvas(sketchConstructor, w, h)
    val parentComponent = if(parentId == null) document.body else document.getElementById(parentId)
    parentComponent?.appendChild(c.canvas)
    c.start()
}