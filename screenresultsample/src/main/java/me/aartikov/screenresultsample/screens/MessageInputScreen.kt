package me.aartikov.screenresultsample.screens

import me.aartikov.alligator.Screen
import me.aartikov.alligator.ScreenResult
import java.io.Serializable

class MessageInputScreen : Screen {
    // It is convenient to declare a result as a static inner class.
    class Result(val message: String) : ScreenResult, Serializable
}