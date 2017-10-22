package me.aartikov.alligator.screenimplementations;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.exceptions.CommandExecutionException;

/**
 * Date: 15.10.2017
 * Time: 11:22
 *
 * @author Artur Artikov
 */

public interface ScreenImplementationVisitor {
	boolean execute(ActivityScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException;

	boolean execute(FragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException;

	boolean execute(DialogFragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws CommandExecutionException;
}
