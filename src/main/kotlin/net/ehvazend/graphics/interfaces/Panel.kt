package net.ehvazend.graphics.interfaces

import javafx.scene.layout.Pane
import net.ehvazend.graphics.Data
import java.util.*

interface Panel {
    val id: String

    val header: Pane
    val body: Pane

    val slides: HashMap<String, Slide>
    val defaultSlide: Slide
    var currentSlide: Slide?

    val backPanel: Panel?
        get() = autoBackPanel()

    val nextPanel: Panel?
        get() = autoNextPanel()

    val setOnLoadPanel: () -> Unit
        get() = {}

    val setOnUnloadPanel: () -> Unit
        get() = {}

    fun loadDefaultSlide(): Pane = Pane().apply {
        id = this@Panel.id
        Slide.load(defaultSlide, this)
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