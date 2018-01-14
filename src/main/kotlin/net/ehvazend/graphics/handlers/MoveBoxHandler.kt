package net.ehvazend.graphics.handlers

import net.ehvazend.graphics.Data
import net.ehvazend.graphics.handlers.AnimationHandler.Effect.toggleDisable
import kotlin.properties.Delegates

object MoveBoxHandler {
    var backButtonEnable: Boolean by Delegates.observable(true) { _, oldValue, newValue ->
        when (!oldValue) {
            newValue -> return@observable
            else -> Data.backButton.toggleDisable()
        }
    }

    var nextButtonEnable: Boolean by Delegates.observable(true) { _, oldValue, newValue ->
        when (!oldValue) {
            newValue -> return@observable
            else -> Data.nextButton.toggleDisable()
        }
    }

    fun allButtonEnable(boolean: Boolean) {
        backButtonEnable = boolean
        nextButtonEnable = boolean
    }
}