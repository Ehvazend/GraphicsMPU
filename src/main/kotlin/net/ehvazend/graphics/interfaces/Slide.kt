package net.ehvazend.graphics.interfaces

import javafx.scene.layout.Pane

interface Slide {
    val body: Pane
    val source: Panel

    fun setAsCurrentSlide() {
        source.currentSlide = this
    }

    companion object {
        fun load(slide: Slide, body: Pane? = null) {
            body?.children ?: slide.source.body.children += slide.body
            if (slide.source.currentSlide != slide) slide.setAsCurrentSlide()
        }

        fun unload(slide: Slide, body: Pane? = null) {
            body?.children ?: slide.source.body.children -= slide.body
            if (slide.source.currentSlide == slide) slide.source.currentSlide = null
        }
    }
}