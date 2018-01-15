package net.ehvazend.graphics.interfaces

import javafx.scene.Node

interface Slide {
    val body: Node
    val source: Panel

    fun setAsCurrentSlide() {
        source.currentSlide = this
    }
}