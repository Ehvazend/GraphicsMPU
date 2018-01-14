package net.ehvazend.graphics

import javafx.fxml.FXMLLoader

fun <T> getRoot(address: String): T = FXMLLoader(Data::class.java.getResource(address)).let {
    it.load<T>()
    it.getRoot<T>()
}