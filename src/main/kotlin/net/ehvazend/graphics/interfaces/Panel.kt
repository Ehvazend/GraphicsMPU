package net.ehvazend.graphics.interfaces

import javafx.scene.Node
import javafx.scene.layout.Pane
import net.ehvazend.graphics.Data
import net.ehvazend.graphics.handlers.AnimationHandler.InstantEffect.instantDisappearance
import java.util.*

interface Panel {
    val id: String

    val header: Node
    val body: Node

    val slides: HashMap<String, Slide>
    val defaultSlide: Slide
    var currentSlide: Slide

    val backPanel: Panel?
        get() = autoBackPanel()

    val nextPanel: Panel?
        get() = autoNextPanel()

    fun fillBody() = Pane().also { pane ->
        pane.id = id

        slides.forEach { key, value ->
            pane.children += value.body.apply { id = key }
            if (value != defaultSlide) value.body.instantDisappearance() else currentSlide = defaultSlide
        }
    }

    fun currentSlide(value: Slide) {
        currentSlide = value
    }

    private fun autoBackPanel() = Data.panels.let { panels ->
        panels.indexOf(this).let {
            if (it != 0) panels[it - 1] else null
        }
    }

    private fun autoNextPanel() = Data.panels.let { panels ->
        panels.indexOf(this).let {
            if (it != panels.lastIndex) panels[it + 1] else null
        }
    }
}