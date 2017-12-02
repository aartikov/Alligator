package me.aartikov.alligator.commands;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementationVisitor;
import me.aartikov.alligator.exceptions.NavigationException;

/**
 * Date: 15.10.2017
 * Time: 12:39
 *
 * @author Artur Artikov
 */

public abstract class ScreenImplementationVisitorCommand implements Command, ScreenImplementationVisitor {
	private Class<? extends Screen> mScreenClass;

	public ScreenImplementationVisitorCommand(Class<? extends Screen> screenClass) {
		mScreenClass = screenClass;
	}

	@Override
	final public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		ScreenImplementation screenImplementation = navigationFactory.getScreenImplementation(mScreenClass);
		if(screenImplementation == null) {
			throw new NavigationException("Screen " + mScreenClass.getSimpleName() + " is unknown.");
		}
		return screenImplementation.accept(this, navigationContext, navigationFactory);
	}
}
