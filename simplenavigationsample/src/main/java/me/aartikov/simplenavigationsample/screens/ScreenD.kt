package me.aartikov.simplenavigationsample.screens

import me.aartikov.alligator.Screen
import java.io.Serializable

// This screen is Serializable, so default fragmentCreationFunction and default screenGettingFunction
// will be able to serialize and deserialize its arguments.
class ScreenD(
    val message: String
) : Screen, Serializable 