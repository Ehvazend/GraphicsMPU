package net.ehvazend.graphics

import javafx.animation.Interpolator
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.effect.ColorAdjust
import javafx.scene.effect.Glow
import javafx.scene.effect.InnerShadow
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import net.ehvazend.graphics.interfaces.Panel

object Data {
    object Config {
        val duration = .75
        val interpolator = Interpolator.SPLINE(1.0, .2, .2, 1.0)!!
    }

    // Stage
    lateinit var stage: Stage

    // Root
    val root: Pane by lazy {
        AnchorPane().also {
            it.prefWidth = 600.0

            it.children += Data.background
            it.children += Data.bodyContainer
            it.children += AnchorPane().also {
                it.layoutX = 14.0
                it.minWidth = 572.0
                it.isPickOnBounds = false

                it.children += headerContainer
                it.children += Data.moveBox
            }
        }
    }

    val background: Rectangle = Rectangle().also {
        // Set
        it.height = 52.0
        it.width = 600.0
        it.fill = LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, Stop(0.0, Color.web("#4377a8")), Stop(1.0, Color.web("#42285b")))

        // Add effects
        // ColorAdjust
        it.effect = ColorAdjust().also {
            it.brightness = 0.25
            it.hue = -1.0
            it.saturation = 0.25

            // Glow
            it.input = Glow().also {
                it.level = 1.0

                // InnerShadow
                it.input = InnerShadow().also {
                    it.color = Color.web("#0000001a")
                    it.offsetY = 2.0
                    it.radius = 0.0
                }
            }
        }
    }

    //-----------------------------------
    // Header
    val headerContainer: Pane = Pane().also {
        it.id = "headerContainer"

        AnchorPane.setBottomAnchor(it, .0)
        AnchorPane.setLeftAnchor(it, .0)
        AnchorPane.setTopAnchor(it, .0)
    }

    // Move box container
    val moveBox: HBox by lazy {
        HBox().also {
            it.id = "moveBox"
            it.spacing = 14.0

            AnchorPane.setBottomAnchor(it, .0)
            AnchorPane.setRightAnchor(it, .0)
            AnchorPane.setTopAnchor(it, .0)

            it.children += backButton
            it.children += nextButton
        }
    }

    val backButton: Button = Button().also {
        it.id = "backButton"
        it.isDisable = true
        it.isMnemonicParsing = false
        it.text = "Back"
    }

    val nextButton: Button = Button().also {
        it.id = "nextButton"
        it.isMnemonicParsing = false
        it.text = "Next"
    }

    //-----------------------------------
    // Panels's container
    val bodyContainer: Pane = Pane().also {
        it.id = "bodyContainer"
        it.relocate(14.0, 37.0)
        it.prefWidth = 572.0
        it.padding = Insets(0.0, 0.0, 14.0, 0.0)
    }

    val panels = ArrayList<Panel>()
    var currentPanel: Panel? = null
}