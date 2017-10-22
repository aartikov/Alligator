package me.aartikov.alligator;

import me.aartikov.alligator.exceptions.CommandExecutionException;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;

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
