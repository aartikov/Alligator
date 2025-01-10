package me.aartikov.simplescreenswitchersample.screens

import me.aartikov.alligator.Screen
import java.io.Serializable

// Screens used by FragmentScreenSwitcher must have equals and hashCode methods correctly overridden.
abstract class TabScreen : Screen, Serializable {

    override fun equals(other: Any?): Boolean {
        return other != null && this.javaClass == other.javaClass
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    class Android : TabScreen()

    class Bug : TabScreen()

    class Dog : TabScreen()
}
