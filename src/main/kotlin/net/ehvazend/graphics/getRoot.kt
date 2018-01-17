package net.ehvazend.graphics

import javafx.fxml.FXMLLoader

fun <T> getRoot(address: String): T = FXMLLoader(Data::class.java.getResource(address)).run {
    load<T>()
    getRoot<T>()
}