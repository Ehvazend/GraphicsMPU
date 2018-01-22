package net.ehvazend.graphics

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import net.ehvazend.graphics.handlers.AnimationHandler
import net.ehvazend.graphics.handlers.AnimationHandler.Add
import net.ehvazend.graphics.handlers.ContentHandler
import net.ehvazend.graphics.interfaces.Panel

abstract class InitializationMPU(contentAppearTime: Double, backgroundEffectTime: Double) : Application() {
    fun initStage(stage: Stage) {
        stage.scene = Scene(Data.root)
        Data.stage = stage
    }

    fun loadPanels(vararg panel: Panel) {
        Data.panels += panel
    }

    fun initPanel(panel: Panel) {
        ContentHandler.initContent(panel)
    }

    init {
        AnimationHandler.Effect.contentAppear(Add(contentAppearTime))
        AnimationHandler.Effect.backgroundEffect(Add(backgroundEffectTime))
    }
}