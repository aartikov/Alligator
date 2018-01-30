package me.aartikov.alligator.commands;

import me.aartikov.alligator.AndroidNavigator;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.navigationfactories.NavigationFactory;

/**
 * Date: 29.12.2016
 * Time: 10:14
 *
 * @author Artur Artikov
 */

/**
 * Command executed by a {@link AndroidNavigator}.
 */
public interface Command {
    /**
     * Executes a command
     *
     * @param navigationContext navigation context that can be used for a command implementation
     * @param navigationFactory navigation factory that can be used for a command implementation
     * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
     * @throws NavigationException if command execution failed
     */
    boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException;

    /**
     * Property meaning the command can be discarded if cannot be executed immediately.
     *
     * @return true if can be discarded if not executed immediately, false otherwise
     */
    boolean discardIfNotImmediate();
}
