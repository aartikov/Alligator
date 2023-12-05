package me.aartikov.alligator.commands

import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.exceptions.NavigationException

/**
 * Command executed by a [AndroidNavigator].
 */
interface Command {
    /**
     * Executes a command
     *
     * @param navigationContext navigation context that can be used for a command implementation
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new [NavigationContext] will be bound
     * @throws NavigationException if command execution failed
     */
    @Throws(NavigationException::class)
    fun execute(navigationContext: NavigationContext): Boolean
}