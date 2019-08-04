package me.aartikov.alligator.commands;

import androidx.annotation.NonNull;

import me.aartikov.alligator.NavigationContext;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.destinations.ActivityDestination;
import me.aartikov.alligator.destinations.Destination;
import me.aartikov.alligator.destinations.DialogFragmentDestination;
import me.aartikov.alligator.destinations.FragmentDestination;
import me.aartikov.alligator.exceptions.NavigationException;
import me.aartikov.alligator.exceptions.ScreenRegistrationException;


abstract class BaseCommand implements Command {
	private Class<? extends Screen> mScreenClass;

	BaseCommand(@NonNull Class<? extends Screen> screenClass) {
		mScreenClass = screenClass;
	}

	abstract protected boolean execute(@NonNull ActivityDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException;

	abstract protected boolean execute(@NonNull FragmentDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException;

	abstract protected boolean execute(@NonNull DialogFragmentDestination destination, @NonNull NavigationContext navigationContext) throws NavigationException;

	@Override
	final public boolean execute(@NonNull NavigationContext navigationContext) throws NavigationException {
		Destination destination = navigationContext.getNavigationFactory().getDestination(mScreenClass);
        if (destination == null) {
			throw new ScreenRegistrationException("Screen " + mScreenClass.getSimpleName() + " is not registered.");
		}

        if (destination instanceof ActivityDestination) {
			return execute((ActivityDestination) destination, navigationContext);
        } else if (destination instanceof FragmentDestination) {
			return execute((FragmentDestination) destination, navigationContext);
        } else if (destination instanceof DialogFragmentDestination) {
			return execute((DialogFragmentDestination) destination, navigationContext);
		} else {
            throw new UnsupportedOperationException("Unsupported destination type " + destination);
		}
	}
}
