package me.aartikov.alligator.listeners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;


/**
 * Transition listener that does nothing.
 */
public class DefaultTransitionListener implements TransitionListener {
	@Override
	public void onScreenTransition(@NonNull TransitionType transitionType, @NonNull DestinationType destinationType,
								   @Nullable Class<? extends Screen> screenClassFrom, @Nullable Class<? extends Screen> screenClassTo) {
	}
}
