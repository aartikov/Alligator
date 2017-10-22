package me.aartikov.alligator;

import me.aartikov.alligator.exceptions.CommandExecutionException;

/**
 * Date: 15.10.2017
 * Time: 11:21
 *
 * @author Artur Artikov
 */

public interface ScreenImplementation {
	boolean accept(ScreenImplementationVisitor visitor, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException;
}
