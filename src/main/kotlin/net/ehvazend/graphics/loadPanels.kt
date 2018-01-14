package net.ehvazend.graphics

import net.ehvazend.graphics.interfaces.Panel

fun loadPanels(vararg panel: Panel) {
    Data.panels += panel
}