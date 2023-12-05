package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;

import me.aartikov.alligator.AndroidNavigator;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.exceptions.NavigationException;


/**
 * Command executed by a {@link AndroidNavigator}.
 */
public interface Command {
	/**
	 * Executes a command
	 *
	 * @param navigationContext navigation context that can be used for a command implementation
	 * @return true if after this command other command can be executed, false if command execution should be delayed until a new {@link NavigationContext} will be bound
	 * @throws NavigationException if command execution failed
	 */
	boolean execute(@NonNull NavigationContext navigationContext) throws NavigationException;
}
