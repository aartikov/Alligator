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
import me.aartikov.alligator.navigationfactories.NavigationFactory;


abstract class BaseCommand implements Command {
	private Class<? extends Screen> mScreenClass;

	BaseCommand(@NonNull Class<? extends Screen> screenClass) {
		mScreenClass = screenClass;
	}

    abstract protected boolean execute(@NonNull ActivityDestination destination, @NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException;

    abstract protected boolean execute(@NonNull FragmentDestination destination, @NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException;

    abstract protected boolean execute(@NonNull DialogFragmentDestination destination, @NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException;

	@Override
	final public boolean execute(@NonNull NavigationContext navigationContext, @NonNull NavigationFactory navigationFactory) throws NavigationException {
        Destination destination = navigationFactory.getDestination(mScreenClass);
        if (destination == null) {
			throw new ScreenRegistrationException("Screen " + mScreenClass.getSimpleName() + " is not registered.");
		}

        if (destination instanceof ActivityDestination) {
            return execute((ActivityDestination) destination, navigationContext, navigationFactory);
        } else if (destination instanceof FragmentDestination) {
            return execute((FragmentDestination) destination, navigationContext, navigationFactory);
        } else if (destination instanceof DialogFragmentDestination) {
            return execute((DialogFragmentDestination) destination, navigationContext, navigationFactory);
		} else {
            throw new UnsupportedOperationException("Unsupported destination type " + destination);
		}
	}
}
