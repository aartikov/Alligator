package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;

import me.aartikov.alligator.FlowScreen;
import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.Destination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.MissingFlowFragmentNavigatorException;
import me.aartikov.alligator.exceptions.MissingFragmentNavigatorException;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;
import me.aartikov.alligator.navigators.ActivityNavigator;
import me.aartikov.alligator.navigators.DialogFragmentNavigator;
import me.aartikov.alligator.navigators.FragmentNavigator;


abstract class BaseCommand implements Command {
	private Class<? extends Screen> mScreenClass;

	BaseCommand(@NonNull Class<? extends Screen> screenClass) {
		mScreenClass = screenClass;
	}

	abstract protected void executeForActivity(@NonNull ActivityDestination destination, @NonNull ActivityNavigator activityNavigator) throws NavigationException;

	abstract protected void executeForFragment(@NonNull FragmentDestination destination, @NonNull FragmentNavigator fragmentNavigator) throws NavigationException;

	abstract protected void executeForDialogFragment(@NonNull DialogFragmentDestination destination, @NonNull DialogFragmentNavigator dialogFragmentNavigator) throws NavigationException;

	@Override
	final public boolean execute(@NonNull NavigationContext navigationContext) throws NavigationException {
		Destination destination = navigationContext.getNavigationFactory().getDestination(mScreenClass);
		if (destination == null) {
			throw new ScreenRegistrationException("Screen " + mScreenClass.getSimpleName() + " is not registered.");
		}

		if (destination instanceof ActivityDestination) {
			executeForActivity((ActivityDestination) destination, navigationContext.getActivityNavigator());
			return false;
		} else if (destination instanceof FragmentDestination) {
			if (FlowScreen.class.isAssignableFrom(mScreenClass)) {
				if (navigationContext.getFlowFragmentNavigator() == null) {
					throw new MissingFlowFragmentNavigatorException();
				}
				executeForFragment((FragmentDestination) destination, navigationContext.getFlowFragmentNavigator());
			} else {
				if (navigationContext.getFragmentNavigator() == null) {
					throw new MissingFragmentNavigatorException();
				}
				executeForFragment((FragmentDestination) destination, navigationContext.getFragmentNavigator());
			}
			return true;
		} else if (destination instanceof DialogFragmentDestination) {
			executeForDialogFragment((DialogFragmentDestination) destination, navigationContext.getDialogFragmentNavigator());
			return true;
		} else {
			throw new UnsupportedOperationException("Unsupported destination type " + destination);
		}
	}
}
