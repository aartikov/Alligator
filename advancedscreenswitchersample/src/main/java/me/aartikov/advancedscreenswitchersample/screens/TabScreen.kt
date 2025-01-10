package me.aartikov.advancedscreenswitchersample.screens

import me.aartikov.alligator.Screen
import java.io.Serializable

// Screens used by FragmentScreenSwitcher must have equals and hashCode methods correctly overridden.
class TabScreen(val name: String) : Screen, Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val tabScreen = other as TabScreen

        return name == tabScreen.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
