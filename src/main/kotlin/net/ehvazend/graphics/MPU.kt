package net.ehvazend.graphics

import javafx.application.Application
import javafx.stage.Stage
import net.ehvazend.graphics.handlers.AnimationHandler
import net.ehvazend.graphics.interfaces.Panel

abstract class MPU : Application() {
    fun initStage(stage: Stage) {
        Data.stage = stage
    }

    fun loadPanels(vararg panel: Panel) {
        Data.panels += panel
    }

    init {
        AnimationHandler.Effect.contentAppear(AnimationHandler.Add(2.5))
        AnimationHandler.Effect.backgroundEffect(AnimationHandler.Add(40.0))
    }
}