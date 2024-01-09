package me.aartikov.alligator.listeners

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult

class DefaultScreenResultListener : ScreenResultListener {
    override fun onScreenResult(screenClass: Class<out Screen>, result: ScreenResult?) {}
}