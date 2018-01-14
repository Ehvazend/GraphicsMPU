package net.ehvazend.graphics.root

import net.ehvazend.graphics.Data
import net.ehvazend.graphics.handlers.AnimationHandler
import net.ehvazend.graphics.handlers.AnimationHandler.Add
import net.ehvazend.graphics.handlers.ContentHandler
import java.net.URL
import java.util.*

class Controller : Annotation() {
    // Late init
    override fun initialize(location: URL, resources: ResourceBundle?) {
        // Invocation override early init
        super.initialize(location, resources)

        // Run
        ContentHandler.initContent(Data.panels.first { it.id == "init" })
        AnimationHandler.Effect.contentAppear(Add(2.5))
        AnimationHandler.Effect.backgroundEffect(Add(40.0))
    }
}