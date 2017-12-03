package me.aartikov.alligator.commands;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.navigationfactories.NavigationFactory;
import me.aartikov.alligator.screenimplementations.ActivityScreenImplementation;
import me.aartikov.alligator.screenimplementations.DialogFragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.FragmentScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementation;
import me.aartikov.alligator.screenimplementations.ScreenImplementationVisitor;

/**
 * Date: 15.10.2017
 * Time: 12:39
 *
 * @author Artur Artikov
 */

abstract class VisitorCommand implements Command {
	private Class<? extends Screen> mScreenClass;

	VisitorCommand(Class<? extends Screen> screenClass) {
		mScreenClass = screenClass;
	}

	abstract protected boolean execute(ActivityScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException;

	abstract protected boolean execute(FragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException;

	abstract protected boolean execute(DialogFragmentScreenImplementation screenImplementation, NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException;

	@Override
	final public boolean execute(NavigationContext navigationContext, NavigationFactory navigationFactory) throws NavigationException {
		ScreenImplementation screenImplementation = navigationFactory.getScreenImplementation(mScreenClass);
		if (screenImplementation == null) {
			throw new NavigationException("Screen " + mScreenClass.getSimpleName() + " is unknown.");
		}
		return screenImplementation.accept(new VisitorAdapter(navigationContext, navigationFactory));
	}

	private class VisitorAdapter implements ScreenImplementationVisitor<Boolean> {
		private NavigationContext mNavigationContext;
		private NavigationFactory mNavigationFactory;

		private VisitorAdapter(NavigationContext navigationContext, NavigationFactory navigationFactory) {
			mNavigationContext = navigationContext;
			mNavigationFactory = navigationFactory;
		}

		@Override
		public Boolean visit(ActivityScreenImplementation screenImplementation) throws NavigationException {
			return execute(screenImplementation, mNavigationContext, mNavigationFactory);
		}

		@Override
		public Boolean visit(FragmentScreenImplementation screenImplementation)throws NavigationException {
			return execute(screenImplementation, mNavigationContext, mNavigationFactory);
		}

		@Override
		public Boolean visit(DialogFragmentScreenImplementation screenImplementation)throws NavigationException {
			return execute(screenImplementation, mNavigationContext, mNavigationFactory);
		}
	}
}
