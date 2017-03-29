package com.art.alligator;

import com.art.alligator.exceptions.CommandExecutionException;

/**
 * Date: 29.12.2016
 * Time: 10:14
 *
 * @author Artur Artikov
 */

/**
 * Navigation command
 */
public interface Command {
	/**
	 * Execute a command
	 * @return true if after this command other commands can be executed, false - otherwise
	 */
	boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException;
}
