package net.ehvazend.graphics.handlers

import net.ehvazend.graphics.Data
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.forceDisable
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.forceEnable
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.toggleDisable
import kotlin.properties.Delegates

object MoveBoxHandler {
    var backButtonEnable: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        if (!holdBackButtonOn.mode) when (oldValue) {
            newValue -> return@observable
            else -> Data.backButton.toggleDisable()
        }
    }

    var nextButtonEnable: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        if (!holdNextButtonOn.mode) when (oldValue) {
            newValue -> return@observable
            else -> Data.nextButton.toggleDisable()
        }
    }

    fun allButtonEnable(boolean: Boolean) {
        backButtonEnable = boolean
        nextButtonEnable = boolean
    }

    data class HoldValue(val mode: Boolean, val holdValue: Boolean)

    var holdBackButtonOn: HoldValue by Delegates.observable(HoldValue(false, false)) { _, oldValue, newValue ->
        if (newValue != oldValue) when (newValue.mode) {
            true -> when (newValue.holdValue) {
                true -> Data.nextButton.forceEnable()
                false -> Data.nextButton.forceDisable()
            }

            false -> when (!nextButtonEnable) {
                true -> Data.nextButton.forceEnable()
                false -> Data.nextButton.forceDisable()
            }
        }
    }

    var holdNextButtonOn: HoldValue by Delegates.observable(HoldValue(false, false)) { _, oldValue, newValue ->
        if (newValue != oldValue) when (newValue.mode) {
            true -> when (newValue.holdValue) {
                true -> Data.nextButton.forceEnable()
                false -> Data.nextButton.forceDisable()
            }

            false -> when (!nextButtonEnable) {
                true -> Data.nextButton.forceEnable()
                false -> Data.nextButton.forceDisable()
            }
        }
    }
}