package me.aartikov.alligator;

/**
 * Date: 26.02.2017
 * Time: 18:51
 *
 * @author Artur Artikov
 */

/**
 * Interface for command execution listening.
 */
public interface NavigationCommandListener {
	/**
	 * Called when a {@link Command} has successfully executed.
	 *
	 * @param command executed command
	 */
	void onNavigationCommandExecuted(Command command);
}
