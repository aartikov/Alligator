package me.aartikov.alligator.screenimplementations;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.exceptions.NavigationException;

/**
 * Date: 15.10.2017
 * Time: 11:21
 *
 * @author Artur Artikov
 */

public interface ScreenImplementation {
	boolean accept(ScreenImplementationVisitor visitor, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException;
}
