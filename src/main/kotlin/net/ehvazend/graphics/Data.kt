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
        const val duration = .75
        val interpolator = Interpolator.SPLINE(1.0, .2, .2, 1.0)!!
    }

    // Stage
    lateinit var stage: Stage

    // Root
    val root: Pane by lazy {
        AnchorPane().apply {
            prefWidth = 600.0

            children += Data.background
            children += Data.bodyContainer
            children += AnchorPane().apply {
                layoutX = 14.0
                minWidth = 572.0
                isPickOnBounds = false

                children += headerContainer
                children += Data.moveBox
            }
        }
    }

    val background: Rectangle = Rectangle().apply {
        // Set
        height = 52.0
        width = 600.0
        fill = LinearGradient(
            0.0,
            0.0,
            1.0,
            1.0,
            true,
            CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#4377a8")),
            Stop(1.0, Color.web("#42285b"))
        )

        // Add effects
        // ColorAdjust
        effect = ColorAdjust().apply {
            brightness = 0.25
            hue = -1.0
            saturation = 0.25

            // Glow
            input = Glow().apply {
                level = 1.0

                // InnerShadow
                input = InnerShadow().apply {
                    color = Color.web("#0000001a")
                    offsetY = 2.0
                    radius = 0.0
                }
            }
        }
    }

    //-----------------------------------
    // Header
    val headerContainer: Pane = Pane().apply {
        id = "headerContainer"

        AnchorPane.setBottomAnchor(this, .0)
        AnchorPane.setLeftAnchor(this, .0)
        AnchorPane.setTopAnchor(this, .0)
    }

    // Move box container
    val moveBox: HBox by lazy {
        HBox().apply {
            id = "moveBox"
            spacing = 14.0

            AnchorPane.setBottomAnchor(this, .0)
            AnchorPane.setRightAnchor(this, .0)
            AnchorPane.setTopAnchor(this, .0)

            children += backButton
            children += nextButton
        }
    }

    val backButton: Button = Button().apply {
        id = "backButton"
        isDisable = true
        isMnemonicParsing = false
        text = "Back"
    }

    val nextButton: Button = Button().apply {
        id = "nextButton"
        isDisable = true
        isMnemonicParsing = false
        text = "Next"
    }

    //-----------------------------------
    // Panels's container
    val bodyContainer: Pane = Pane().apply {
        id = "bodyContainer"
        relocate(14.0, 37.0)
        prefWidth = 572.0
        padding = Insets(0.0, 0.0, 14.0, 0.0)
    }

    val panels = ArrayList<Panel>()
    var currentPanel: Panel? = null
}