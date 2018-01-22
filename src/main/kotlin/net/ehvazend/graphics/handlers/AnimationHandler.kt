package net.ehvazend.graphics.handlers

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.DoubleProperty
import javafx.beans.value.WritableValue
import javafx.scene.Node
import javafx.scene.effect.ColorAdjust
import javafx.util.Duration
import net.ehvazend.graphics.Data

object AnimationHandler {
    // Allows smoothly change value
    data class Add(val duration: Double? = null, val interpolator: Interpolator? = null,
                   val isAutoReverse: Boolean? = null, val cycleCount: Int? = null)

    fun <T> WritableValue<T>.timeline(values: Pair<T, T>, add: Add? = null) = Timeline().let {
        // Set up KeyFrame and adding in to Timeline
        it.keyFrames += KeyFrame(
                Duration.seconds(add?.duration ?: Data.Config.duration),
                // Set up KeyValue
                KeyValue(this.apply { value = values.first }, values.second, add?.interpolator ?: Interpolator.EASE_BOTH)
        )

        // Set up Timeline
        it.isAutoReverse = add?.isAutoReverse ?: false
        it.cycleCount = add?.cycleCount ?: 0

        // Return and play
        it.also(Timeline::play)
    }

    fun DoubleProperty.goTo(pair: Pair<Double, Double>) = this.timeline(pair, Add(interpolator = Data.Config.interpolator))

    object Effect {
        fun Node.appearance(add: Add? = null, initialValue: Double? = this.opacity)
                = this.opacityProperty().timeline(initialValue to 1.0, add).also { isMouseTransparent = false }

        fun Node.disappearance(add: Add? = null, initialValue: Double? = this.opacity)
                = this.opacityProperty().timeline(initialValue to 0.0, add).also { isMouseTransparent = true }

        fun Node.enable(add: Add? = null): Timeline? = if (this.isDisable) {
            this.opacityProperty().timeline(0.5 to 1.0, add).apply {
                isMouseTransparent = false
                setOnFinished { isDisable = false }
            }
        } else null

        fun Node.disable(add: Add? = null): Timeline? = if (!this.isDisable) {
            println("This - $this, isDisable - ${this.isDisable}")
            this.opacityProperty().timeline(1.0 to 0.5, add).apply {
                isMouseTransparent = true
                setOnFinished {
                    isMouseTransparent = false
                    isDisable = true
                }
            }
        } else null

        fun Node.toggleDisable(add: Add? = null) = when (this.isDisable) {
            true -> this.enable(add)
            false -> this.disable(add)
        }

        fun Node.forceEnable(add: Add? = null): Timeline? =
                this.opacityProperty().timeline(0.5 to 1.0, add).apply {
                    isMouseTransparent = false
                    setOnFinished { isDisable = false }
                }

        fun Node.forceDisable(add: Add? = null): Timeline? =
                this.opacityProperty().timeline(1.0 to 0.5, add).apply {
                    isMouseTransparent = true
                    setOnFinished {
                        isMouseTransparent = false
                        isDisable = true
                    }
                }

        fun Node.forceToggleDisable(add: Add? = null) = when (this.isDisable) {
            true -> this.forceEnable(add)
            false -> this.forceDisable(add)
        }


        fun backgroundEffect(add: Add): Timeline {
            return (Data.background.effect as ColorAdjust).hueProperty().timeline(0.0 to 1.0,
                    add.copy(isAutoReverse = true, cycleCount = -1))
        }

        fun contentAppear(add: Add? = null) {
            Data.background.appearance(add!!.copy(duration = add.duration!! / 1.5), 0.0)
            Data.headerContainer.appearance(add, 0.0)
            Data.moveBox.appearance(add, 0.0)
            Data.bodyContainer.appearance(add, 0.0)
        }
    }

    object InstantEffect {
        fun Node.instantAppearance() = this.apply {
            opacity = 1.0
            isMouseTransparent = false
        }

        fun Node.instantDisappearance() = this.apply {
            opacity = .0
            isMouseTransparent = true
        }
    }
}