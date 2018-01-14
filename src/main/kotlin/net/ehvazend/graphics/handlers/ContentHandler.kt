package net.ehvazend.graphics.handlers

import javafx.application.Platform
import javafx.scene.Node
import net.ehvazend.graphics.Data
import net.ehvazend.graphics.handlers.AnimationHandler.Add
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.appearance
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.disappearance
import net.ehvazend.graphics.handlers.AnimationHandler.timeline
import net.ehvazend.graphics.handlers.MoveBoxHandler.allButtonEnable
import net.ehvazend.graphics.interfaces.Panel
import net.ehvazend.graphics.interfaces.Slide

object ContentHandler {
    fun initContent(panel: Panel) {
        loadContent(panel)
        initMoveBox()
        resizeBackground()
    }

    private fun loadContent(panel: Panel): Pair<Node, Node> {
        fun loadHeader(panel: Panel) = panel.header.also {
            Data.headerContainer.children += it
        }

        fun loadBody(panel: Panel) = panel.body.also {
            Data.bodyContainer.children += it
        }

        return loadHeader(panel) to loadBody(panel)
                .apply { Data.currentPanel = panel }
    }

    private fun initMoveBox() {
        fun refreshState() {
            if (Data.currentPanel == null) allButtonEnable(false) else {
                MoveBoxHandler.backButtonEnable = Data.currentPanel?.backPanel != null
                MoveBoxHandler.nextButtonEnable = Data.currentPanel?.nextPanel != null
            }
        }

        Data.backButton.setOnAction {
            panelBack()
            refreshState()
        }

        Data.nextButton.setOnAction {
            panelNext()
            refreshState()
        }

        // Initial call
        refreshState()
    }

    private fun resizeBackground() {
        when {
            Data.root.height != 0.0 -> Data.background.height = Data.root.height
            Data.root.height == 0.0 -> {
                Platform.runLater {
                    Data.background.height = Data.root.height
                }
            }
        }
    }

    // Slide zone -------------------------------
    sealed class Direction(val x: Double = 0.0, val y: Double = 0.0) {
        object TOP : Direction(y = -Data.stage.scene.height)
        object RIGHT : Direction(x = Data.stage.scene.width)
        object BOTTOM : Direction(y = Data.stage.scene.height)
        object LEFT : Direction(x = -Data.stage.scene.width)
    }

    private fun slideStep(slides: Pair<Slide, Slide>, direction: Direction) {
        val (newSlide, oldSlide) = slides

        oldSlide.slide.also {
            it.disappearance()
            it.layoutYProperty().timeline(0.0 to direction.y, Add(interpolator = Data.Config.interpolator))
        }

        newSlide.slide.also {
            it.appearance()
            it.layoutYProperty().timeline(-direction.y to 0.0, Add(interpolator = Data.Config.interpolator))
        }

        newSlide.source.currentSlide(newSlide)
    }

    fun slideNext(slides: Pair<Slide, Slide>) = slideStep(slides, Direction.BOTTOM)
    fun slideBack(slides: Pair<Slide, Slide>) = slideStep(slides, Direction.TOP)

    // Panel zone -------------------------------
    private fun panelStep(panels: Pair<Panel, Panel>, direction: Direction) {
        val (newPanel, oldPanel) = panels

        // Load new objects
        loadContent(newPanel).toList().forEach { it.opacity = 0.0 }

        // Header
        oldPanel.header.disappearance(Add(Data.Config.duration / 2.0)).setOnFinished {
            newPanel.header.opacity = 0.0
            newPanel.header.appearance(Add(Data.Config.duration / 2.0))
        }

        // Body
        oldPanel.body.disappearance()
        oldPanel.body.layoutXProperty().timeline(0.0 to direction.x, Add(interpolator = Data.Config.interpolator)).setOnFinished {
            // Delete old objects when they are behind Data.root scene
            Data.bodyContainer.children.remove(oldPanel.body)
            Data.headerContainer.children.remove(oldPanel.header)
        }

        newPanel.body.appearance()
        newPanel.body.layoutXProperty().timeline(-direction.x to 0.0, AnimationHandler.Add(interpolator = Data.Config.interpolator))
    }

    private fun panelBack() {
        panelStep(Data.currentPanel!!.backPanel!! to Data.currentPanel!!, Direction.RIGHT)
    }

    private fun panelNext() {
        panelStep(Data.currentPanel!!.nextPanel!! to Data.currentPanel!!, Direction.LEFT)
    }
}
