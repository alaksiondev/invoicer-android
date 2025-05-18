package io.github.alaksion.invoicer.foundation.navigation.extensions

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

/**
 * Push screen to the front of the stack removing previous screens of the same type
 * */
inline fun <reified T : Screen> Navigator.pushToFront(destination: T) {
    val popOldInstances = items.filter { it !is T }
    val newStack = popOldInstances + destination
    replaceAll(newStack)
}
