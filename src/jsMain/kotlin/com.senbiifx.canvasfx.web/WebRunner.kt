package com.senbiifx.canvasfx.web

import com.senbiifx.canvasfx.core.SketchConstructor
import kotlinx.browser.document


fun show(sketchConstructor: SketchConstructor, w: Double = 640.0, h: Double = 320.0, parentId: String? = null){
    val c = WebAnimationCanvas(sketchConstructor, w, h)
    val parentComponent = if(parentId == null) document.body else document.getElementById(parentId)
    parentComponent?.appendChild(c.canvas)
    c.start()
}