package net.ehvazend.graphics.handlers

import javafx.application.Platform
import javafx.scene.Node
import net.ehvazend.graphics.Data
import net.ehvazend.graphics.handlers.AnimationHandler.Add
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.appearance
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.disappearance
import net.ehvazend.graphics.handlers.AnimationHandler.InstantEffect.instantDisappearance
import net.ehvazend.graphics.handlers.AnimationHandler.goTo
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

    // Direction for slideStep and panelStep
    private enum class Direction(val x: Double? = null, val y: Double? = null) {
        TOP(y = -Data.stage.scene.height),
        RIGHT(x = Data.stage.scene.width),
        BOTTOM(y = Data.stage.scene.height),
        LEFT(x = -Data.stage.scene.width)
    }

    // Slide zone -------------------------------
    private fun slideStep(slides: Pair<Slide, Slide>, direction: Direction) {
        val (newSlide, oldSlide) = slides.toList()
        val target = direction.y!!

        // Move oldSlide
        oldSlide.body.apply {
            disappearance()
            layoutYProperty().goTo(0.0 to target)
        }

        // Move newSlide
        newSlide.body.apply {
            appearance()
            layoutYProperty().goTo(-target to 0.0)
        }

        // Set currentSlide
        newSlide.setAsCurrentSlide()
    }

    fun slideNext(slides: Pair<Slide, Slide>) = slideStep(slides, Direction.BOTTOM)
    fun slideBack(slides: Pair<Slide, Slide>) = slideStep(slides, Direction.TOP)

    // Panel zone -------------------------------
    private fun panelStep(panels: Pair<Panel, Panel>, direction: Direction) {
        val (newPanel, oldPanel) = panels
        val target = direction.x!!

        // Load new objects and disappearance it
        loadContent(newPanel).toList().forEach { it.instantDisappearance() }

        // Header logic
        oldPanel.header.disappearance(Add(Data.Config.duration / 2.0))
            .setOnFinished { newPanel.header.appearance(Add(Data.Config.duration / 2.0)) }

        // Body logic
        oldPanel.body.disappearance()
        oldPanel.body.layoutXProperty().goTo(0.0 to target).setOnFinished {
            // Delete old objects when they are behind Data.root scene
            Data.bodyContainer.children.remove(oldPanel.body)
            Data.headerContainer.children.remove(oldPanel.header)
        }

        newPanel.body.appearance()
        newPanel.body.layoutXProperty().goTo(-target to 0.0)
    }

    private fun panelBack() = panelStep(Data.currentPanel!!.backPanel!! to Data.currentPanel!!, Direction.RIGHT)
    private fun panelNext() = panelStep(Data.currentPanel!!.nextPanel!! to Data.currentPanel!!, Direction.LEFT)
}
