package me.aartikov.alligator.listeners;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.aartikov.alligator.DestinationType;
import me.aartikov.alligator.Screen;
import me.aartikov.alligator.TransitionType;


/**
 * Interface for listening of screen transition.
 */
public interface TransitionListener {
	/**
	 * Is called when an usual screen transition (not screen switching and not dialog showing) has been executed.
	 *
	 * @param transitionType  type of a transition
	 * @param destinationType destination type of screens involved in the transition
	 * @param screenClassFrom class of the screen that disappears during a transition or {@code null} if there was no current screen before transition
	 * @param screenClassTo   class of the screen that appears during a transition or {@code null} if there was no previous screen before transition
	 */
	void onScreenTransition(@NonNull TransitionType transitionType,
							@NonNull DestinationType destinationType,
							@Nullable Class<? extends Screen> screenClassFrom,
							@Nullable Class<? extends Screen> screenClassTo);
}
